package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.RuleSetDao;
import com.michalowicz.inzynierka.dao.TournamentDao;
import com.michalowicz.inzynierka.dto.CreateTournamentForm;
import com.michalowicz.inzynierka.entity.Match;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.TournamentStatus;
import com.michalowicz.inzynierka.entity.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TournamentService {
    @Resource
    TournamentDao tournamentDao;

    @Resource
    RuleSetDao ruleSetDao;

    @Resource
    MatchService matchService;

    Random random = new Random();


    public List<Tournament> getAllTournaments() {
        return tournamentDao.findAll();
    }

    public void createTournament(CreateTournamentForm form, User user) throws Exception {
        try {
            ruleSetDao.save(form.getRuleSet());
            Tournament tournament = new Tournament();
            tournament.setName(form.getName());
            tournament.setDescription(form.getDescription());
            tournament.addOwner(user);
            tournament.addRuleSet(form.getRuleSet());

            tournamentDao.save(tournament);
        } catch (Exception e) {
            throw new Exception("Błąd danych");
        }
    }

    public Tournament getTournamentWithId(Long id) throws NotFoundException {
        Tournament tournament = tournamentDao.getById(id);
        if (tournament != null) {
            return tournament;
        }
        throw new NotFoundException("Nie znaleziono turnieju");
    }

    public void addTeam(final Tournament tournament, final Team team) {
        tournament.addTeam(team);
        tournamentDao.save(tournament);
    }

    public void startTournament(final Tournament tournament) {
        List<Team> teams = new ArrayList<>(tournament.getTeams());
        Collections.shuffle(teams);
        if (teams.size() == tournament.getTeamsNeeded()) {
            for (int i = 0; i < teams.size(); i += 2) {
                Match match = new Match();
//                matchService.generateRounds(match,tournament.getRuleSet().getRoundsToWin(),tournament.getRuleSet().getPointsToWin());
                match.setTeamOne(teams.get(i));
                match.setTeamTwo(teams.get(i + 1));
                tournament.addMatch(match);
            }
            tournament.setStatus(TournamentStatus.Trwający);
            tournamentDao.save(tournament);
        }
    }

    public void advance(final Tournament tournament, final Team winner, final int level) {
        int finalLevel = (int) ((int) Math.log(tournament.getTeamsNeeded()) / Math.log(2));
        if (level == finalLevel) {
            tournament.setWinner(winner);
            tournament.setStatus(TournamentStatus.Zakończony);
        } else {
            List<Match> matches = tournament.getMatches().stream().filter(match -> match.getLevel() == level + 1 && match.getTeamOne() != null && match.getTeamTwo() == null).collect(Collectors.toList());
            if (matches.size() == 0) {
                Match match = new Match();
                match.setTeamOne(winner);
                match.setLevel(level + 1);
                tournament.addMatch(match);
            } else {
                Match match = matches.get(random.nextInt(matches.size()));
                match.setTeamTwo(winner);
            }
        }
        tournamentDao.save(tournament);

    }
}
