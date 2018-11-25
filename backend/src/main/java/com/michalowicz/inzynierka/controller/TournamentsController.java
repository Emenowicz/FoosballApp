package com.michalowicz.inzynierka.controller;

import com.michalowicz.inzynierka.dto.CreateTournamentForm;
import com.michalowicz.inzynierka.dto.NewTeamForm;
import com.michalowicz.inzynierka.entity.Team;
import com.michalowicz.inzynierka.entity.Tournament;
import com.michalowicz.inzynierka.entity.User;
import com.michalowicz.inzynierka.service.TeamService;
import com.michalowicz.inzynierka.service.TournamentService;
import com.michalowicz.inzynierka.service.UserService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentsController {

    @Resource
    TournamentService tournamentService;

    @Resource
    UserService userService;
    @Resource
    TeamService teamService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllTournaments() {
        List<Tournament> sortedTournaments = tournamentService.getAllTournaments();
        sortedTournaments.sort(Comparator.comparing(Tournament::getStatus));
        return new ResponseEntity(sortedTournaments, HttpStatus.OK);
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

    @RequestMapping(value = "/{tournamentId}", method = RequestMethod.GET)
    public ResponseEntity getTournamentById(@PathVariable("tournamentId") Long id) {
        try {
            Tournament tournament = tournamentService.getTournamentWithId(id);
            return new ResponseEntity(tournament, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{tournamentId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTournament(@PathVariable("tournamentId") Long id, Principal principal) {
        User loggedUser = userService.getLoggedUser(principal.getName());
        tournamentService.deleteTournamentWithId(id, loggedUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{tournamentId}/createTeam", method = RequestMethod.POST)
    public ResponseEntity createTeam(@PathVariable("tournamentId") Long id, @RequestBody NewTeamForm form, Principal principal) {
        try {
            User user = userService.getLoggedUser(principal.getName());
            Tournament tournament = tournamentService.getTournamentWithId(id);
            if (tournament.getTeams().size() != tournament.getTeamsNeeded()) {
                Team team = teamService.createNewTeam(form, tournament, user);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity("Liczba drużyn jest za duża, dołącz do istniejącej", HttpStatus.FORBIDDEN);
            }
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{tournamentId}/start", method = RequestMethod.POST)
    public ResponseEntity startTournament(@PathVariable("tournamentId") Long id, Principal principal) {
        try {
            Tournament tournament = tournamentService.getTournamentWithId(id);
            User loggedUser = userService.getLoggedUser(principal.getName());
            if (tournament.getOwner().equals(loggedUser)) {
                tournamentService.startTournament(tournament);
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
