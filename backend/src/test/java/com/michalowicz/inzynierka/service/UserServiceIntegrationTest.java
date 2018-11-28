package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.dao.UserDao;
import com.michalowicz.inzynierka.dto.UpdatePasswordForm;
import com.michalowicz.inzynierka.dto.UpdateUserDetailsForm;
import com.michalowicz.inzynierka.entity.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    UserService userService;
    @Resource
    UserDao userDao;

    @After
    public void afterActions(){
        userDao.delete(userDao.findByUsername("test"));
    }

    @Test
    public void shouldRegisterUser() throws Exception {
//        given
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.test");
        user.setPassword("test");
//        when
        userService.registerUser(user);
        User registeredUser = userDao.findByUsername("test");
//        then
        assertNotNull(registeredUser);
    }

    @Test(expected = Exception.class)
    public void shouldNotRegisterUserSecondTime() throws Exception {
//        given
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.test");
        user.setPassword("test");
//        when
        userService.registerUser(user);
        userService.registerUser(user);
//        then expectException
    }

    @Test
    public void shouldUpdateUserDetails() throws Exception {
//        given
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.test");
        user.setPassword("test");
        UpdateUserDetailsForm form = new UpdateUserDetailsForm();
        form.setEmail("test2@test2.test2");
//        when
        userService.registerUser(user);
        userService.updateUserDetails(user, form);
        User changedUser = userDao.findByUsername("test");
//        then
        assertEquals("test2@test2.test2", changedUser.getEmail());
    }


    @Test
    public void shouldUpdatePassword() throws Exception {
//        given
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.test");
        user.setPassword("test");
        UpdatePasswordForm form = new UpdatePasswordForm();
        form.setCurrentPassword("test");
        form.setNewPassword("test2");
//        when
        userService.registerUser(user);
        userService.updatePassword(user, form);
        User changedUser = userDao.findByUsername("test");
//        then
        assertTrue(passwordEncoder.matches("test2", changedUser.getPassword()));
    }
}