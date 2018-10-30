package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Team {

    @Id
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"teams"})
    private List<User> players;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"teams"})
    private Tournament tournament;

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

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(final List<User> players) {
        this.players = players;
    }

    public void addPlayer(final User user) {
        this.getPlayers().add(user);
        user.addTeam(this);
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(final Tournament tournament) {
        this.tournament = tournament;
    }

    public void addTournament(final Tournament tournament) {
        this.tournament = tournament;
        tournament.getTeams().add(this);
    }
}
