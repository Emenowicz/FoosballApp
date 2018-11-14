package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isPrivate;

    private int wins = 0;

    private int loses = 0;

    private int played = 0;

    private int roundsWin = 0;

    private int roundsLost = 0;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"teams", "joinedTournaments", "ownedTournaments", "usergroups"})
    private Set<User> players = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
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

    public Set<User> getPlayers() {
        return players;
    }

    public void setPlayers(final Set<User> players) {
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(final boolean privacy) {
        this.isPrivate = privacy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getPlayed() {
        return played;
    }

    public int getRoundsWin() {
        return roundsWin;
    }

    public int getRoundsLost() {
        return roundsLost;
    }

    public void addWin() {
        this.played++;
        this.wins++;
    }
    public void addLose() {
        this.played++;
        this.loses++;
    }

    public void addRoundWin(int roundsWin){
        this.roundsWin+=roundsWin;
    }


}
