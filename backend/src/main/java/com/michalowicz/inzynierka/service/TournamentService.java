package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.RuleSetDao;
import com.michalowicz.inzynierka.dao.TournamentDao;
import com.michalowicz.inzynierka.dto.CreateTournamentForm;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TournamentService {
    @Resource
    TournamentDao tournamentDao;

    @Resource
    RuleSetDao ruleSetDao;

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
}
