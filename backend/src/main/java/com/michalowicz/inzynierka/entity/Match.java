package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int level = 0;

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

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonIgnoreProperties("match")
    private List<Round> rounds = new ArrayList<>();

    private String status = "Open";

    private LocalDateTime closedTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timeCreated = LocalDateTime.now();

    private int scoreOne;

    private int scoreTwo;

    @ManyToOne
    @JsonIgnoreProperties({"tournament"})
    private Team winner;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
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


    public int getScoreTwo() {
        return scoreTwo;
    }

    public String getStatus() {
        return status;
    }

    public Team getWinner() {
        return winner;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setScoreOne(final int scoreOne) {
        this.scoreOne = scoreOne;
    }

    public void setScoreTwo(final int scoreTwo) {
        this.scoreTwo = scoreTwo;
    }

    public void setWinner(final Team winner) {
        this.winner = winner;
    }

    public LocalDateTime getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(final LocalDateTime closedTime) {
        this.closedTime = closedTime;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }
}
