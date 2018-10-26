package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dto.UpdatePasswordForm;
import com.michalowicz.inzynierka.dto.UpdateUserDetailsForm;
import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.repository.UserDao;
import com.michalowicz.inzynierka.repository.UsergroupDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    @Resource
    UsergroupDao usergroupDao;

    @Resource
    PasswordEncoder passwordEncoder;

    public List<UserModel> getAllUsers() {
        return userDao.findAll();
    }

    public void registerUser(UserModel userModel) throws Exception {
        if (userDao.findByUsername(userModel.getUsername()) == null) {
            userModel.addUsergroup(usergroupDao.findByNameIgnoreCase("user"));
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userDao.save(userModel);
        } else {
            throw new Exception("User with that username already exists");
        }
    }

    public void updateUserDetails(UserModel userModel, UpdateUserDetailsForm form) throws Exception {
        UserModel conflictUser = userDao.findByUsername(form.getUsername());
        if (conflictUser != null && !conflictUser.equals(userModel)) {
            throw new Exception("Login zajęty");
        }
        conflictUser = userDao.findByEmail(form.getEmail());
        if (conflictUser != null && !conflictUser.equals(userModel)) {
            throw new Exception("Email zajęty");
        }
        try {
            userModel.setUsername(form.getUsername());
            userModel.setEmail(form.getEmail());
            userDao.save(userModel);
        } catch (Exception e) {
            throw new Exception("Błąd danych");
        }

    }

    public UserModel getLoggedUser(String username) {
        return userDao.findByUsername(username);
    }

    public void updatePassword(final UserModel loggedUser, final UpdatePasswordForm form) throws Exception {
        if (passwordEncoder.matches(form.getCurrentPassword(), loggedUser.getPassword())) {
            loggedUser.setPassword(passwordEncoder.encode(form.getNewPassword()));
            userDao.save(loggedUser);
        } else {
            throw new Exception("Aktualne hasło niepoprawne");
        }
    }
}
