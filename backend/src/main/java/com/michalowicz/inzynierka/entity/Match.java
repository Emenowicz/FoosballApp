package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnoreProperties({"matches", "teams"})
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnoreProperties("tournament")
    private Team teamOne;
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnoreProperties("tournament")
    private Team teamTwo;

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JsonIgnoreProperties("match")
    private List<Round> rounds = new ArrayList<>();

    @Transient
    private int scoreOne;

    @Transient
    private int scoreTwo;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Team getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(final Team teamOne) {
        this.teamOne = teamOne;
    }


    public Team getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(final Team teamTwo) {
        this.teamTwo = teamTwo;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(final List<Round> rounds) {
        this.rounds = rounds;
    }

    public void addRound(Round round) {
        this.rounds.add(round);
        round.setMatch(this);
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(final Tournament tournament) {
        this.tournament = tournament;
    }

    public void addTournament(Tournament tournament) {
        this.tournament = tournament;
        tournament.getMatches().add(this);
    }

    public int getScoreOne() {
        return scoreOne;
    }

    @PostLoad
    public void setScores() {
        scoreOne = 0;
        scoreTwo = 0;
        rounds.forEach(round -> {
            if (round.getScoreTeamOne() == round.getPointsToWin()) {
                scoreOne++;
            } else if (round.getScoreTeamTwo() == round.getPointsToWin()) {
                scoreTwo++;
            }
        });

    }

    public int getScoreTwo() {
        return scoreTwo;
    }

}
