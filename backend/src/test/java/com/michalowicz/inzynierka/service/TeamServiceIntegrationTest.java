package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.RuleSetDao;
import com.michalowicz.inzynierka.dao.TeamDao;
import com.michalowicz.inzynierka.dao.TournamentDao;
import com.michalowicz.inzynierka.dao.UserDao;
import com.michalowicz.inzynierka.dto.NewTeamForm;
import com.michalowicz.inzynierka.entity.RuleSet;
import com.michalowicz.inzynierka.entity.RuleSetType;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceIntegrationTest {

    @Resource
    RuleSetDao ruleSetDao;
    @Resource
    UserDao userDao;
    @Resource
    TournamentDao tournamentDao;
    @Resource
    TeamDao teamDao;
    @Resource
    TeamService teamService;

    RuleSet ruleSet;
    User user;
    Tournament tournament;

    @Before
    public void beforeActions() {
        ruleSet = new RuleSet();
        ruleSet.setName("testRuleSet");
        ruleSet.setPointsToWin(5);
        ruleSet.setRoundsToWin(3);
        ruleSet.setTeamSize(2);
        ruleSet.setRuleSetType(RuleSetType.DEFAULT);
        ruleSetDao.save(ruleSet);
        user = new User();
        user.setUsername("testUser");
        user.setEmail("test@test.test");
        user.setPassword("test");
        userDao.save(user);
        tournament = new Tournament();
        tournament.setName("testTournament");
        tournament.setOwner(user);
        tournament.setTeamsNeeded(4);
        tournament.addRuleSet(ruleSet);
        tournamentDao.save(tournament);
    }
//
//    @After
//    public void afterActions() {
//        tournamentDao.delete(tournament);
////        userDao.delete(user);
//        ruleSetDao.delete(ruleSet);
//    }

    @Test
    public void shouldCreateNewOpenTeam() throws Exception {
//        given
        NewTeamForm form = new NewTeamForm();
        form.setName("testTeam");
        form.setPrivacy("open");
//        when
        Team team = teamService.createNewTeam(form, tournament, user);
        Team result = teamDao.getById(team.getId());
//        then
        assertEquals(form.getName(), result.getName());
    }

    @Test
    public void shouldCreateNewPrivateTeam() throws Exception {
//        given
        NewTeamForm form = new NewTeamForm();
        form.setName("testTeam");
        form.setPrivacy("private");
        form.setPassword("password");
//        when
        Team team = teamService.createNewTeam(form, tournament, user);
        Team result = teamDao.getById(team.getId());
//        then
        assertEquals(form.getName(), result.getName());
        assertTrue(result.isPrivate());
        assertEquals(form.getPassword(), result.getPassword());
    }

    @Test
    public void shouldAddPlayer() throws Exception {
//        given
        NewTeamForm form = new NewTeamForm();
        form.setName("testTeam");
        form.setPrivacy("private");
        form.setPassword("password");
        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@user2.user2");
        user2.setPassword("password");
        userDao.save(user2);
//        when
        Team team = teamService.createNewTeam(form, tournament, user);
        teamService.addPlayer(team, user2);
        Team result = teamDao.getById(team.getId());
//        then
        assertEquals(2, result.getPlayers().size());
    }

    @Test(expected = Exception.class)
    public void shouldNotAddPlayerSecondTime() throws Exception {
//        given
        NewTeamForm form = new NewTeamForm();
        form.setName("testTeam");
        form.setPrivacy("private");
        form.setPassword("password");
//        when
        Team team = teamService.createNewTeam(form, tournament, user);
        teamService.addPlayer(team, user);
//        then expect exception
    }

    @Test
    public void shouldRemoveTeam() throws Exception {
//        given
        NewTeamForm form = new NewTeamForm();
        form.setName("testTeam");
        form.setPrivacy("private");
        form.setPassword("password");
//        when
        Team team = teamService.createNewTeam(form, tournament, user);
        Long teamId = team.getId();
        teamService.removePlayerFromTeam(teamId, user);
//        then
        assertNull(teamDao.getById(teamId));
    }
}