package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.entity.Match;
import com.michalowicz.inzynierka.entity.Round;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    public void generateRounds(Match match, int roundsQuantity, final int pointsToWin) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < (roundsQuantity*2)-1; i++) {
            Round round = new Round();
            round.setPointsToWin(pointsToWin);
            rounds.add(round);
        }
        match.setRounds(rounds);
    }
}
