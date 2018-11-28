package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.MatchDao;
import com.michalowicz.inzynierka.entity.Match;
import com.michalowicz.inzynierka.entity.Round;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.validators.RoundValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class MatchService {
    @Resource
    MatchDao matchDao;
    @Resource
    RoundValidator roundValidator;

    public List<Match> getAwaitingMatches(User user) {
        List<Match> matches = matchDao.getAllByStatus("Open");
        matches = matches.stream().filter(match -> match.getTeamOne().getPlayers().contains(user) ||
                (match.getTeamTwo() != null && match.getTeamTwo().getPlayers().contains(user))
        ).collect(Collectors.toList());
        return matches;
    }

    public Match getMatchWithId(Long id) {
        return matchDao.getById(id);
    }

    @Transactional
    public void finishMatch(final Match match, final List<Round> rounds) throws Exception {
        List<Round> validRounds = rounds.stream().filter(round -> roundValidator.validateRound(round)).collect(Collectors.toList());
        if(rounds.size()==(match.getTournament().getRuleSet().getRoundsToWin()*2)-1){
            if(validRounds.size()==rounds.size()){
                validRounds.remove(validRounds.size()-1);
            }
            Round lastRound = rounds.get(rounds.size()-1);
            if(roundValidator.validateLastRound(lastRound)){
                validRounds.add(lastRound);
            }
        }
        if (validRounds.size() != rounds.size()) {
            throw new Exception("Wprowadzono błędne wyniki");
        }
        int pointsTeamOne = validRounds.stream().filter(round -> round.getScoreTeamOne() >= round.getPointsToWin()).collect(Collectors.toList()).size();
        int pointsTeamTwo = validRounds.stream().filter(round -> round.getScoreTeamTwo() >= round.getPointsToWin()).collect(Collectors.toList()).size();
        int neededPoints = match.getTournament().getRuleSet().getRoundsToWin();
        if (pointsTeamOne >= neededPoints || pointsTeamTwo >= neededPoints) {
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
            if (round.getScoreTeamOne() >= round.getPointsToWin()) {
                scoreOne.getAndIncrement();
            } else if (round.getScoreTeamTwo() >= round.getPointsToWin()) {
                scoreTwo.getAndIncrement();
            }
        });
        match.setScoreOne(scoreOne.get());
        match.setScoreTwo(scoreTwo.get());
        if (match.getScoreOne() == match.getTournament().getRuleSet().getRoundsToWin() || match.getScoreTwo() == match.getTournament().getRuleSet().getRoundsToWin()) {
            match.setStatus("Closed");
            match.setClosedTime(LocalDateTime.now());
        }
        if (match.getStatus().equals("Closed")) {
            if (scoreOne.get() > scoreTwo.get()) {
                match.getTeamOne().addWin();
                match.getTeamTwo().addLose();
                match.setWinner(match.getTeamOne());
            } else {
                match.getTeamOne().addLose();
                match.getTeamTwo().addWin();
                match.setWinner(match.getTeamTwo());
            }
        }
        match.getTeamOne().addRoundWin(scoreOne.get());
        match.getTeamOne().addRoundLost(scoreTwo.get());
        match.getTeamTwo().addRoundWin(scoreTwo.get());
        match.getTeamTwo().addRoundLost(scoreOne.get());
    }

    public List<Match> getUsersLastMatches(final User loggedUser) {
        return getUsersMatchHistory(loggedUser).stream().limit(5).collect(Collectors.toList());
    }

    public List<Match> getUsersMatchHistory(final User loggedUser) {
        List<Match> matches = matchDao.getAllByStatus("Closed");
        matches = matches.stream().filter(match -> match.getTeamOne().getPlayers().contains(loggedUser) ||
                match.getTeamTwo().getPlayers().contains(loggedUser)
        ).collect(Collectors.toList());
        matches.sort(Comparator.comparing(Match::getClosedTime).reversed());
        return matches;
    }

}
