package com.michalowicz.inzynierka.data;

import com.michalowicz.inzynierka.dao.MatchDao;
import com.michalowicz.inzynierka.dao.RuleSetDao;
import com.michalowicz.inzynierka.dao.TeamDao;
import com.michalowicz.inzynierka.dao.TournamentDao;
import com.michalowicz.inzynierka.dao.UserDao;
import com.michalowicz.inzynierka.dao.UsergroupDao;
import com.michalowicz.inzynierka.entity.RuleSet;
import com.michalowicz.inzynierka.entity.RuleSetType;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.entity.Usergroup;
import com.michalowicz.inzynierka.service.TournamentService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class InitialDataLoader implements ApplicationRunner {

    @Resource
    UserDao userDao;
    @Resource
    UsergroupDao usergroupDao;
    @Resource
    TournamentDao tournamentDao;
    @Resource
    RuleSetDao ruleSetDao;
    @Resource
    TeamDao teamDao;
    @Resource
    MatchDao matchDao;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    TournamentService tournamentService;

    Random random = new Random();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //USERGROUPS
        Usergroup usergroup = new Usergroup("USER");
        usergroupDao.save(usergroup);


        //USERS
        List<User> users = new ArrayList<>();
        User dawid = new User("dawid", passwordEncoder.encode("dawid"), "dawid@dawid.dawid");
        dawid.addUsergroup(usergroup);
        users.add(dawid);
        for (int i = 0; i < 50; i++) {
            String login = "user" + i;
            String email = login + "@" + login + login;
            users.add(new User(login, passwordEncoder.encode(login), email, usergroup));
        }
        userDao.saveAll(users);

        //RULESETS
        RuleSet standardRuleSet = new RuleSet("Standardowy", RuleSetType.DEFAULT, 5, 3, 2);
        ruleSetDao.save(standardRuleSet);

        //TOURNAMENTS
        String[] tournamentNames = {"Wyborna rozgrywka Kielce", "Nowa nazwa turnieju", "Robimy turniej", "Soccer 11", "MiniSoccer18", "Fajna gra", "Turniej Polski", "Turniej Czeski", "Weekendowe granie", "Na śmierć i życie", "Teksańska masakra piłkarzykami", "Piłkarzyki 2011", "Firmowe granie", "PWr piłkarzyki", "Zagrajmy w grę"};
        List<Tournament> tournaments = new ArrayList<>();
        for (String tournamentName : tournamentNames) {
            Tournament tournament = new Tournament(tournamentName);
            tournament.addOwner(users.get(random.nextInt(users.size())));
            tournament.addRuleSet(standardRuleSet);
            tournament.setTeamsNeeded(8);
            tournaments.add(tournament);
        }
        tournamentDao.saveAll(tournaments);

        //PLAYERS
        String[] teamNames = {"Super drużyna", "Najlepsi", "TurboGracze", "Janusze", "Drużyna śmierci", "Andrzej", "Tygrysy", "Cośtam"};
        List<Team> teams = new ArrayList<>();
        for (Tournament tournament : tournaments) {
            for (String teamName : teamNames) {
                Team team = new Team();
                team.setName(teamName);
                team.addTournament(tournament);
                for (int i = 0; i < tournament.getRuleSet().getTeamSize(); i++) {
                    while (team.getPlayers().size() != tournament.getRuleSet().getTeamSize()) {
                        User userToTeam =users.get(random.nextInt(users.size()));
                        if(!tournament.getParticipants().contains(userToTeam)){
                            team.addPlayer(userToTeam);
                        }
                    }
                    teams.add(team);
                }
            }
        }
        teamDao.saveAll(teams);

        //MATCHES
        for (int i=0; i<tournaments.size(); i+=3){
            tournamentService.startTournament(tournaments.get(i));
        }

        //DAWID
        Tournament dawidTournament = new Tournament("Dawdziakowy turniej");
        dawidTournament.addOwner(dawid);
        dawidTournament.addRuleSet(standardRuleSet);
        dawidTournament.setTeamsNeeded(2);
        tournamentDao.save(dawidTournament);
        Team team = new Team();
        team.setName("dawdziakowyTeam");
        team.setPrivate(true);
        team.setPassword("password");
        team.addPlayer(dawid);
        team.addPlayer(users.get(random.nextInt(users.size())));
        team.addTournament(dawidTournament);
        teamDao.save(team);

        Team team2 = new Team();
        team2.setName("antydawdziaki");
        team2.addPlayer(users.get(random.nextInt(users.size())));
        team2.addPlayer(users.get(random.nextInt(users.size())));
        team2.addTournament(dawidTournament);
        teamDao.save(team2);

    }
}

