package com.michalowicz.inzynierka.controllers;

import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserModel> getLoggedUser(Principal principal){
        UserModel loggedUser = userService.getLoggedUser(principal.getName());

        return loggedUser!=null ? new ResponseEntity<>(loggedUser,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<List<UserModel>> addUser(@RequestBody @Valid UserModel user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
