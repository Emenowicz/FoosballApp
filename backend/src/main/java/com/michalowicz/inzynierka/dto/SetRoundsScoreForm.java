package com.michalowicz.inzynierka.dto;

import com.michalowicz.inzynierka.entity.Round;

import java.util.List;

public class SetRoundsScoreForm {
    private List<Round> rounds;

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(final List<Round> rounds) {
        this.rounds = rounds;
    }
}
