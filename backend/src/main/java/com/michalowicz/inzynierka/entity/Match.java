package com.michalowicz.inzynierka.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Team teamOne;
    @ManyToOne
    private Team teamTwo;

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER)
    private List<Round> rounds = new ArrayList<>();

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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(final Tournament tournament) {
        this.tournament = tournament;
    }

    public void addTournament(Tournament tournament){
        this.tournament = tournament;
        tournament.getMatches().add(this);
    }


}
