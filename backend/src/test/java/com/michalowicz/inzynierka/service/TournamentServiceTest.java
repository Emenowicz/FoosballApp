package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.RuleSetDao;
import com.michalowicz.inzynierka.dao.TournamentDao;
import com.michalowicz.inzynierka.dto.CreateTournamentForm;
import com.michalowicz.inzynierka.entity.Match;
import com.michalowicz.inzynierka.entity.RuleSet;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.TournamentStatus;
import com.michalowicz.inzynierka.entity.User;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TournamentServiceTest {

    @Mock
    TournamentDao tournamentDao;
    @Mock
    RuleSetDao ruleSetDao;
    @InjectMocks
    TournamentService tournamentService;

    @Test
    public void shouldCreateTournament() throws Exception {
//        given
        CreateTournamentForm tournamentForm = new CreateTournamentForm();
        tournamentForm.setName("TestName");
        tournamentForm.setDescription("TestDescription");
        tournamentForm.setRuleSet(Mockito.mock(RuleSet.class));
        tournamentForm.setTeamsNeeded(2);
        User user = new User();
        when(tournamentDao.getByName("TestName")).thenReturn(null);
//        when
        tournamentService.createTournament(tournamentForm, user);
//        then
        Mockito.verify(tournamentDao).save(Mockito.any());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException() throws Exception {
//        given
        CreateTournamentForm tournamentForm = new CreateTournamentForm();
        tournamentForm.setName("TestName");
        tournamentForm.setDescription("TestDescription");
        tournamentForm.setRuleSet(Mockito.mock(RuleSet.class));
        tournamentForm.setTeamsNeeded(2);
        User user = new User();
        when(tournamentDao.getByName("TestName")).thenReturn(Mockito.mock(Tournament.class));
//        when
        tournamentService.createTournament(tournamentForm, user);
//        then exception
    }


    @Test
    public void shouldStartTournament() {
//        given
        Tournament tournament = new Tournament();
        tournament.setTeamsNeeded(2);
        List<Team> mockTeams = new ArrayList<>();
        mockTeams.add(Mockito.mock(Team.class));
        mockTeams.add(Mockito.mock(Team.class));
        tournament.setTeams(mockTeams);
//        when
        tournamentService.startTournament(tournament);
//        then
        Mockito.verify(tournamentDao).save(tournament);
        assertEquals(TournamentStatus.Trwający, tournament.getStatus());
    }

    @Test
    public void shouldNotStartTournament() {
//        given
        Tournament tournament = new Tournament();
        tournament.setTeamsNeeded(4);
        List<Team> mockTeams = new ArrayList<>();
        mockTeams.add(Mockito.mock(Team.class));
        mockTeams.add(Mockito.mock(Team.class));
        tournament.setTeams(mockTeams);
//        when
        tournamentService.startTournament(tournament);
//        then
        Mockito.verify(tournamentDao, never()).save(tournament);
        assertNotEquals(TournamentStatus.Trwający, tournament.getStatus());

    }

    @Test
    public void shouldDeleteTournament() {
//        given
        Tournament tournament = new Tournament();
        tournament.setId(999L);
        User user = new User();
        tournament.setOwner(user);
        when(tournamentDao.getById(999L)).thenReturn(tournament);
//        when
        tournamentService.deleteTournamentWithId(999L, user);
//        then
        Mockito.verify(tournamentDao).deleteById(999L);
    }

    @Test
    public void shouldNotDeleteTournament() {
//        given
        Tournament tournament = new Tournament();
        tournament.setId(999L);
        User user = new User();
        User owner = new User();
        tournament.setOwner(owner);
        when(tournamentDao.getById(999L)).thenReturn(tournament);
//        when
        tournamentService.deleteTournamentWithId(999L, user);
//        then
        Mockito.verify(tournamentDao, never()).deleteById(999L);
    }

    @Test
    public void shouldReturnTournamentForId() throws NotFoundException {
//        given
        Tournament tournament = new Tournament();
        when(tournamentDao.getById(999L)).thenReturn(tournament);
//        when
        Tournament result = tournamentService.getTournamentWithId(999L);
//        then
        assertNotNull(result);
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotFindTournamentAndThrowNotFoundException() throws NotFoundException {
//        given
        when(tournamentDao.getById(999L)).thenReturn(null);
//        when
        Tournament result = tournamentService.getTournamentWithId(999L);
//        then
        assertNotNull(result);
    }

    @Test
    public void shouldSetWinnerAndFinishTournament() {
//        given
        Tournament tournament = new Tournament();
        tournament.setTeamsNeeded(2);
        Team winnerTeam = new Team();
        int level = 0;
//        when
        tournamentService.advance(tournament, winnerTeam, level);
//        then
        assertEquals(winnerTeam, tournament.getWinner());
        assertEquals(TournamentStatus.Zakończony,tournament.getStatus());
        Mockito.verify(tournamentDao).save(tournament);
    }

    @Test
    public void shouldCreateNewMatch(){
//        given
        Tournament tournament = new Tournament();
        tournament.setTeamsNeeded(4);
        Team winnerTeam = new Team();
        int level = 0;
//        when
        tournamentService.advance(tournament, winnerTeam, level);
//        then
        assertEquals(1, tournament.getMatches().size());
        Mockito.verify(tournamentDao).save(tournament);
    }

    @Test
    public void shouldAddTeamToExistingMatch(){
//        given
        Tournament tournament = new Tournament();
        tournament.setTeamsNeeded(4);
        Match match = new Match();
        match.setLevel(1);
        match.setTeamOne(new Team());
        tournament.getMatches().add(match);
        Team winnerTeam = new Team();
        int level = 0;
//        when
        tournamentService.advance(tournament, winnerTeam, level);
//        then
        assertEquals(winnerTeam, match.getTeamTwo());
        Mockito.verify(tournamentDao).save(tournament);
    }

}