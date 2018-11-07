package com.michalowicz.inzynierka.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Match match;

    int scoreTeamOne;

    int scoreTeamTwo;

    int maxScore;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(final Match match) {
        this.match = match;
    }

    public int getScoreTeamOne() {
        return scoreTeamOne;
    }

    public void setScoreTeamOne(final int scoreTeamOne) {
        this.scoreTeamOne = scoreTeamOne;
    }

    public int getScoreTeamTwo() {
        return scoreTeamTwo;
    }

    public void setScoreTeamTwo(final int scoreTeamTwo) {
        this.scoreTeamTwo = scoreTeamTwo;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(final int maxScore) {
        this.maxScore = maxScore;
    }
}
