package com.michalowicz.inzynierka.validators;

import com.michalowicz.inzynierka.entity.Round;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class RoundValidatorTest {

    @Test
    public void shouldValidationForNormalRoundPass() {
//        given
        RoundValidator roundValidator = new RoundValidator();
        Round round = new Round();
        round.setScoreTeamOne(4);
        round.setScoreTeamTwo(5);
        round.setPointsToWin(5);
//        when
        boolean result = roundValidator.validateRound(round);
//        then
        assertTrue(result);
    }

    @Test
    public void shouldValidationForNormalRoundFail() {
//        given
        RoundValidator roundValidator = new RoundValidator();
        Round round = new Round();
        round.setScoreTeamOne(0);
        round.setScoreTeamTwo(4);
        round.setPointsToWin(5);
//        when
        boolean result = roundValidator.validateRound(round);
//        then
        assertFalse(result);
    }

    @Test
    public void shouldValidationForLastRoundPass() {
//        given
        RoundValidator roundValidator = new RoundValidator();
        Round round = new Round();
        round.setScoreTeamOne(7);
        round.setScoreTeamTwo(8);
        round.setPointsToWin(5);
//        when
        boolean result = roundValidator.validateLastRound(round);
//        then
        assertTrue(result);
    }

    @Test
    public void shouldValidationForLastRoundFail() {
//        given
        RoundValidator roundValidator = new RoundValidator();
        Round round = new Round();
        round.setScoreTeamOne(4);
        round.setScoreTeamTwo(5);
        round.setPointsToWin(5);
//        when
        boolean result = roundValidator.validateLastRound(round);
//        then
        assertFalse(result);
    }

    @Test
    public void shouldNotAllowToTie(){
//        given
        RoundValidator roundValidator = new RoundValidator();
        Round round = new Round();
        round.setScoreTeamOne(5);
        round.setScoreTeamTwo(5);
        round.setPointsToWin(5);
        Round roundLast = new Round();
        roundLast.setScoreTeamOne(8);
        roundLast.setScoreTeamTwo(8);
        round.setPointsToWin(5);
//        when
        boolean resultNormal = roundValidator.validateRound(round);
        boolean resultLast = roundValidator.validateLastRound(round);
//        then
        assertFalse(resultNormal);
        assertFalse(resultLast);

    }
}