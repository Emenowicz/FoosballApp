package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.dto.SetRoundsScoreForm;
import com.michalowicz.inzynierka.entity.Match;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.service.MatchService;
import com.michalowicz.inzynierka.service.TournamentService;
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
import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    @Resource
    MatchService matchService;
    @Resource
    UserService userService;
    @Resource
    TournamentService tournamentService;

    @RequestMapping(value = "awaiting", method = RequestMethod.GET)
    public ResponseEntity getAwaitingMatches(Principal principal) {
        User loggedUser = userService.getLoggedUser(principal.getName());
        List<Match> awaitingMatches = matchService.getAwaitingMatches(loggedUser);
        return new ResponseEntity(awaitingMatches, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/setScore", method = RequestMethod.POST)
    public ResponseEntity saveScore(@PathVariable("id") Long matchId, Principal principal, @RequestBody SetRoundsScoreForm form) {
        User loggedUser = userService.getLoggedUser(principal.getName());
        Match match = matchService.getMatchWithId(matchId);
        if(match.getStatus().equals("Closed")){
            return new ResponseEntity("Mecz został już zamknięty wcześniej", HttpStatus.FORBIDDEN);
        }
        if (match != null && (match.getTeamOne().getPlayers().contains(loggedUser) || match.getTeamTwo().getPlayers().contains(loggedUser))) {
            try {
                matchService.finishMatch(match, form.getRounds());
                if (match.getStatus().equals("Closed")) {
                    tournamentService.advance(match.getTournament(), match.getWinner(), match.getLevel());
                }
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity("Wystąpił błąd, Spróbuj ponownie później", HttpStatus.FORBIDDEN);
        }
    }
}
