package com.michalowicz.inzynierka.controllers;

import com.michalowicz.inzynierka.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tournaments")
public class TournamentsController {

    @Resource
    TournamentService tournamentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllTournaments(){
        return new ResponseEntity(tournamentService.getAllTournaments(), HttpStatus.OK);
    }
}
