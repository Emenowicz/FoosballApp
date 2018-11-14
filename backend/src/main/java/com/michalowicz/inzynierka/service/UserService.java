package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dto.UpdatePasswordForm;
import com.michalowicz.inzynierka.dto.UpdateUserDetailsForm;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.dao.UserDao;
import com.michalowicz.inzynierka.dao.UsergroupDao;
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

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void registerUser(User user) throws Exception {
        if (userDao.findByUsername(user.getUsername()) == null) {
            user.addUsergroup(usergroupDao.findByNameIgnoreCase("user"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
        } else {
            throw new Exception("User with that username already exists");
        }
    }

    public void updateUserDetails(User user, UpdateUserDetailsForm form) throws Exception {

        User conflictUser = userDao.findByEmail(form.getEmail());
        if (conflictUser != null && !conflictUser.equals(user)) {
            throw new Exception("Email zajęty");
        }
        try {
            user.setEmail(form.getEmail());
            userDao.save(user);
        } catch (Exception e) {
            throw new Exception("Błąd danych");
        }

    }

    public User getLoggedUser(String username) {
        return userDao.findByUsername(username);
    }

    public void updatePassword(final User loggedUser, final UpdatePasswordForm form) throws Exception {
        if (passwordEncoder.matches(form.getCurrentPassword(), loggedUser.getPassword())) {
            loggedUser.setPassword(passwordEncoder.encode(form.getNewPassword()));
            userDao.save(loggedUser);
        } else {
            throw new Exception("Aktualne hasło niepoprawne");
        }
    }


}
