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

@Service
public class TournamentService {
    @Resource
    TournamentDao tournamentDao;

    @Resource
    RuleSetDao ruleSetDao;

    @Resource
    MatchService matchService;


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
            for (int i = 0; i <teams.size(); i+=2) {
                Match match = new Match();
//                matchService.generateRounds(match,tournament.getRuleSet().getRoundsToWin(),tournament.getRuleSet().getPointsToWin());
                match.setTeamOne(teams.get(i));
                match.setTeamTwo(teams.get(i+1));
                tournament.addMatch(match);
            }
            tournament.setStatus(TournamentStatus.Trwający);
            tournamentDao.save(tournament);
        }
    }
}
