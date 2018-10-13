package com.michalowicz.inzynierka.data;

import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.entity.UsergroupModel;
import com.michalowicz.inzynierka.repository.UserDao;
import com.michalowicz.inzynierka.repository.UsergroupDao;
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
    UsergroupDao usergroupDao;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UsergroupModel adminUsergroup = new UsergroupModel("admin");
        UsergroupModel userUsergroup = new UsergroupModel("user");
        usergroupDao.save(adminUsergroup);
        usergroupDao.save(userUsergroup);
        UserModel user1 = new UserModel("admin", passwordEncoder.encode("admin"));
        user1.addUsergroup(adminUsergroup);
        userDao.save(user1);
        UserModel user2 = new UserModel("dawid",passwordEncoder.encode("dawid"));
        user2.addUsergroup(userUsergroup);
        userDao.save(user2);

    }
}
