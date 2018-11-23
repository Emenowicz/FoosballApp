package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.service.StatisticsService;
import com.michalowicz.inzynierka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    UserService userService;
    @Resource
    StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUserStatistics(Principal principal){
        User loggedUser =userService.getLoggedUser(principal.getName());
        return new ResponseEntity(statisticsService.getStatisticsForUser(loggedUser), HttpStatus.OK);
    }
}
