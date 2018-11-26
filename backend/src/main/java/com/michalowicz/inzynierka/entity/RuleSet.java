package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RuleSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name = "Niestandardowy";

    @Enumerated(EnumType.STRING)
    private RuleSetType ruleSetType = RuleSetType.CUSTOM;

    @Min(1)
    private int pointsToWin;

    @Min(1)
    private int roundsToWin;

    @Min(1)
    private int teamSize;

    @OneToMany(mappedBy = "ruleSet",fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"ruleSet"})
    private Set<Tournament> tournaments = new HashSet<>();

    public RuleSet() {
    }

    public RuleSet(final int pointsToWin, final int roundsToWin, final int teamSize) {
        this.pointsToWin = pointsToWin;
        this.roundsToWin = roundsToWin;
        this.teamSize = teamSize;
    }

    public RuleSet(final String name, final RuleSetType type, final int pointsToWin, final int roundsToWin, final int teamSize) {
        this.name = name;
        this.ruleSetType = type;
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

    public void addTournament(final Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.addRuleSet(this);
    }

    public RuleSetType getRuleSetType() {
        return ruleSetType;
    }

    public void setRuleSetType(final RuleSetType ruleSetType) {
        this.ruleSetType = ruleSetType;
    }
}
