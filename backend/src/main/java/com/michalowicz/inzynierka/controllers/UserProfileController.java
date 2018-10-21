package com.michalowicz.inzynierka.controllers;

import com.michalowicz.inzynierka.entity.UserModel;
import com.michalowicz.inzynierka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class UserProfileController {

    @Resource
    UserService userService;


}
