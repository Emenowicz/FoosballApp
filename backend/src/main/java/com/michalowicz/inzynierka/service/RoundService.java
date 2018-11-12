package com.michalowicz.inzynierka.service;

import com.michalowicz.inzynierka.entity.Round;
import org.springframework.stereotype.Service;

@Service
public class RoundService {
    public boolean validateRound(Round round) {
        return round.getScoreTeamOne() != round.getScoreTeamTwo()
                && round.getScoreTeamOne() != 0
                && round.getScoreTeamTwo() != 0
                && round.getScoreTeamOne() <= round.getPointsToWin()
                && round.getScoreTeamTwo() <= round.getPointsToWin()
                && (round.getScoreTeamOne() == round.getPointsToWin() || round.getScoreTeamTwo() == round.getPointsToWin());
    }
}
