package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.service.TeamService;
import com.michalowicz.inzynierka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Resource
    TeamService teamService;
    @Resource
    UserService userService;

    @RequestMapping(value = "/{teamId}/join", method = RequestMethod.POST)
    public ResponseEntity joinToTournament(@PathVariable("teamId") Long teamId, @RequestBody(required = false) String teamPassword, Principal principal) {
        try {
            Team team = teamService.getTeam(teamId);
            User loggedUser = userService.getLoggedUser(principal.getName());
            if (team.isPrivate() && teamPassword != null) {
                if (!team.getPassword().equals(teamPassword)) {
                    throw new Exception("Hasło się nie zgadza");
                }
            }
            teamService.addPlayer(team, loggedUser);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
