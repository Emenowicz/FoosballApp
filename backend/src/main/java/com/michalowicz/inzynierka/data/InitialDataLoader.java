package com.michalowicz.inzynierka.data;

import com.michalowicz.inzynierka.dao.TournamentDao;
import com.michalowicz.inzynierka.dao.UserDao;
import com.michalowicz.inzynierka.dao.UsergroupDao;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.entity.Usergroup;
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
    PasswordEncoder passwordEncoder;

    Random random = new Random();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //USERGROUPS
        Usergroup usergroup = new Usergroup("USER");
        usergroupDao.save(usergroup);


        //USERS
        List<User> users = new ArrayList<>();
        User dawid = new User("dawid", passwordEncoder.encode("dawid"), "dawid@dawid.dawid");
        users.add(dawid);
        for (int i = 0; i < 30; i++) {
            String login = "user" + i;
            String email = login + "@" + login + login;
            users.add(new User(login, passwordEncoder.encode(login), email, usergroup));
        }
        userDao.saveAll(users);


        //TOURNAMENTS
        String[] tournamentNames = {"Wyborna rozgrywka Kielce", "Nowa nazwa turnieju", "Robimy turniej", "Soccer 11", "MiniSoccer18", "Fajna gra", "Turniej Polski", "Turniej Czeski", "Weekendowe granie", "Na śmierć i życie", "Teksańska masakra grillem piłkarzykowym", "Piłkarzyki 2011", "Firmowe granie", "PWr piłkarzyki", "Zagrajmy w grę"};
        List<Tournament> tournaments = new ArrayList<>();
        for (String tournamentName : tournamentNames) {
            Tournament tournament = new Tournament(tournamentName);
            tournament.addOwner(users.get(random.nextInt(users.size())));
            for(int i = 0; i<10; i++){
                tournament.addParticipant(users.get(random.nextInt(users.size())));
            }
            tournaments.add(tournament);
        }

        tournamentDao.saveAll(tournaments);


        //        user1.addUsergroup(adminUsergroup);
//        userDao.save(user1);
//        Tournament tournament = new Tournament("turnej dawida");
//        user2.addOwnedTournament(tournament);
//        tournamentDao.save(tournament);
//        user2.joinToTournament(tournament);
//        userDao.save(user2);

    }
}
