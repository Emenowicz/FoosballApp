package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.repository.UserDao;
import com.michalowicz.inzynierka.repository.UsergroupDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    @Resource
    UsergroupDao usergroupDao;

    public List<UserModel> getAllUsers() {
        return userDao.findAll();
    }

    public void registerUser(UserModel userModel) throws Exception {
        if (userDao.findByUsername(userModel.getUsername()) == null) {
            userModel.addUsergroup(usergroupDao.findByNameIgnoreCase("user"));
            userDao.save(userModel);
        } else {
            throw new Exception("User with that username already exists");
        }
    }
}
