package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.dto.UpdatePasswordForm;
import com.michalowicz.inzynierka.dto.UpdateUserDetailsForm;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User loggedUser = getLoggedUser(principal);

        return loggedUser != null ? new ResponseEntity<>(loggedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody @Valid User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/updateDetails", method = RequestMethod.POST)
    public ResponseEntity updateDetails(@RequestBody UpdateUserDetailsForm form, Principal principal) {
        try {
            userService.updateUserDetails(getLoggedUser(principal), form);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updatePassword", method=RequestMethod.POST)
    public ResponseEntity updatePassword(@RequestBody UpdatePasswordForm form, Principal principal) {
        try {
            userService.updatePassword(getLoggedUser(principal), form);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    private User getLoggedUser(Principal principal) {
        return userService.getLoggedUser(principal.getName());
    }
}
