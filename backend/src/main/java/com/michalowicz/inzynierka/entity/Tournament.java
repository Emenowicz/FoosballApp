package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status = TournamentStatus.Otwarty;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timeCreated = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value = {"ownedTournaments", "joinedTournaments"})
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value = {"joinedTournaments", "ownedTournaments"})
    private Set<User> participants = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"tournament"})
    private Set<Team> teams = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private RuleSet ruleSet;

    public Tournament() {
    }

    public Tournament(@NotBlank final String name) {
        this.name = name;
    }

    public Tournament(@NotBlank final String name, final User owner) {
        this.name = name;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    public void addOwner(final User owner) {
        this.owner = owner;
        owner.getOwnedTournaments().add(this);
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(final TournamentStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(final LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(final Set<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(final User user) {
        this.participants.add(user);
        user.getJoinedTournaments().add(this);
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(final Set<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(final Team team) {
        this.teams.add(team);
        team.setTournament(this);
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(final RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public void addRuleSet(final RuleSet ruleSet) {
        this.ruleSet = ruleSet;
        ruleSet.getTournaments().add(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
