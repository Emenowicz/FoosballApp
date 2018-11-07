package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.TeamDao;
import com.michalowicz.inzynierka.dto.NewTeamForm;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeamService {

    @Resource
    TeamDao teamDao;

    public Team createNewTeam(final NewTeamForm teamForm, final Tournament tournament, User user) {
        Team team = new Team();
        team.setName(teamForm.getName());
        team.setPrivate(teamForm.getPrivacy().equals("private"));
        if (team.isPrivate()) {
            team.setPassword(teamForm.getPassword());
        }
        team.addTournament(tournament);
        team.addPlayer(user);
        teamDao.save(team);
        return team;
    }

    public Team getTeam(final Long teamId) {
        return teamDao.getById(teamId);

    }

    public void addPlayer(final Team team, final User user) throws Exception {
        if(!team.getTournament().getParticipants().contains(user)){
            team.addPlayer(user);
            teamDao.save(team);
        } else {
            throw new Exception("Użytkownik jest już dołączony do turnieju");
        }
    }

}
