package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.dto.CreateTournamentForm;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.service.TournamentService;
import com.michalowicz.inzynierka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
@RequestMapping("/tournaments")
public class TournamentsController {

    @Resource
    TournamentService tournamentService;

    @Resource
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllTournaments() {
        return new ResponseEntity(tournamentService.getAllTournaments(), HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity addNewTournament(@RequestBody CreateTournamentForm form, Principal principal) {
        try {
            User loggedUser = userService.getLoggedUser(principal.getName());
            tournamentService.createTournament(form, loggedUser);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
