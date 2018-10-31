package com.michalowicz.inzynierka.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RuleSet {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int pointsToWin;

    private int roundsToWin;

    private int teamSize;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Tournament> tournaments = new HashSet<>();

    public RuleSet() {
    }

    public RuleSet(final String name, final int pointsToWin, final int roundsToWin, final int teamSize) {
        this.name = name;
        this.pointsToWin = pointsToWin;
        this.roundsToWin = roundsToWin;
        this.teamSize = teamSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPointsToWin() {
        return pointsToWin;
    }

    public void setPointsToWin(final int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }

    public int getRoundsToWin() {
        return roundsToWin;
    }

    public void setRoundsToWin(final int roundsToWin) {
        this.roundsToWin = roundsToWin;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(final int teamSize) {
        this.teamSize = teamSize;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(final Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void addTournament(final Tournament tournament){
        this.tournaments.add(tournament);
        tournament.addRuleSet(this);
    }
}
