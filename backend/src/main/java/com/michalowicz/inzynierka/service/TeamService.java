package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.TeamDao;
import com.michalowicz.inzynierka.dto.NewTeamForm;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.TournamentStatus;
import com.michalowicz.inzynierka.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeamService {

    @Resource
    TeamDao teamDao;

    public Team createNewTeam(final NewTeamForm teamForm, final Tournament tournament, User user) throws Exception {
        Team team = new Team();
        if(tournament.getTeams().stream().anyMatch(t -> t.getName().equals(teamForm.getName()))){
            throw new Exception("Nazwa zespołu zajęta");
        }
        team.setName(teamForm.getName());
        team.setPrivate(teamForm.getPrivacy().equals("private"));
        if (team.isPrivate()) {
            team.setPassword(teamForm.getPassword());
        }
        team.addTournament(tournament);
        if(tournament.getParticipants().stream().anyMatch(p -> p.equals(user))) {
            throw new Exception("Gracz już bierze udział w turnieju");
        }
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
            throw new Exception("Gracz już bierze udział w turnieju");
        }
    }

    public void removePlayerFromTeam(final Long teamId, final User loggedUser) throws Exception {
        Team team = teamDao.getById(teamId);
        if(team.getTournament().getStatus().equals(TournamentStatus.Otwarty)){
            if(team.getPlayers().size()<=1){
                teamDao.delete(team);
            } else {
                team.getPlayers().remove(loggedUser);
                teamDao.save(team);
            }
        }else{
            throw new Exception("Wystąpił błąd");
        }
    }
}
