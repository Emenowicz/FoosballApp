package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.repository.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    public List<UserModel> getAllUsers(){
        return userDao.findAll();
    }
}
