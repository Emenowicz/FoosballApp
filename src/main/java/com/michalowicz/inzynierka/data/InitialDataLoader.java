package com.michalowicz.inzynierka.data;

import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.entity.UsergroupModel;
import com.michalowicz.inzynierka.repository.UserDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitialDataLoader implements ApplicationRunner {

    @Resource
    UserDao userDao;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UsergroupModel usergroupModel = new UsergroupModel("admin");
        UserModel user1 = new UserModel("admin", passwordEncoder.encode("admin"));
        user1.addUsergroup(usergroupModel);
        userDao.save(user1);
        UserModel user2 = new UserModel("dawid",passwordEncoder.encode("dawid"));
        user1.addUsergroup(usergroupModel);
        userDao.save(user2);

    }
}
