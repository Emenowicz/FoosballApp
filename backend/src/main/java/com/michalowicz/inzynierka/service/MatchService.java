package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.MatchDao;
import com.michalowicz.inzynierka.entity.Match;
import com.michalowicz.inzynierka.entity.Round;
import com.michalowicz.inzynierka.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class MatchService {
    @Resource
    MatchDao matchDao;
    @Resource
    RoundService roundService;

    public void generateRounds(Match match, int roundsQuantity, final int pointsToWin) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < (roundsQuantity * 2) - 1; i++) {
            Round round = new Round();
            round.setPointsToWin(pointsToWin);
            rounds.add(round);
        }
        match.setRounds(rounds);
    }

    public List<Match> getAwaitingMatches(User user) {
        List<Match> matches = matchDao.findAll();
        matches = matches.stream().filter(match -> match.getStatus().equals("Open")
                && (match.getTeamOne().getPlayers().contains(user) ||
                (match.getTeamTwo() != null && match.getTeamTwo().getPlayers().contains(user))
        )).collect(Collectors.toList());
        return matches;
    }

    public Match getMatchWithId(Long id) {
        return matchDao.getById(id);
    }

    @Transactional
    public void finishMatch(final Match match, final List<Round> rounds) throws Exception {
        List<Round> validRounds = rounds.stream().filter(round -> roundService.validateRound(round)).collect(Collectors.toList());
        if (validRounds.size() != rounds.size()) {
            throw new Exception("Wprowadzono błędne wyniki");
        }
        int pointsTeamOne = validRounds.stream().filter(round -> round.getScoreTeamOne() == round.getPointsToWin()).collect(Collectors.toList()).size();
        int pointsTeamTwo = validRounds.stream().filter(round -> round.getScoreTeamTwo() == round.getPointsToWin()).collect(Collectors.toList()).size();
        int neededPoints = match.getTournament().getRuleSet().getRoundsToWin();
        if (pointsTeamOne == neededPoints || pointsTeamTwo == neededPoints) {
            setStatus(match, rounds);
            matchDao.save(match);
        } else {
            throw new Exception("Wprowadzono błędne wyniki");
        }

    }

    private void setStatus(Match match, List<Round> rounds) {
        final AtomicInteger scoreOne = new AtomicInteger();
        final AtomicInteger scoreTwo = new AtomicInteger();
        rounds.forEach(round -> {
            round.addMatch(match);
            if (round.getScoreTeamOne() == round.getPointsToWin()) {
                scoreOne.getAndIncrement();
            } else if (round.getScoreTeamTwo() == round.getPointsToWin()) {
                scoreTwo.getAndIncrement();
            }
        });
        match.setScoreOne(scoreOne.get());
        match.setScoreTwo(scoreTwo.get());
        match.setStatus(match.getScoreOne() == match.getTournament().getRuleSet().getRoundsToWin() || match.getScoreTwo() == match.getTournament().getRuleSet().getRoundsToWin() ? "Closed" : "Open");
        if (match.getStatus().equals("Closed")) {
            match.setWinner(scoreOne.get() > scoreTwo.get() ? match.getTeamOne() : match.getTeamTwo());
        }
    }

}
