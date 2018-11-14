package com.michalowicz.inzynierka.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    @JsonIgnore
    private String password;
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnoreProperties(value = {"users"})
    private Set<Usergroup> usergroups = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"owner"})
    private Set<Tournament> ownedTournaments = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = {"participants"})
    private Set<Tournament> joinedTournaments = new HashSet<>();

    @ManyToMany(mappedBy = "players", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"players"})
    private List<Team> teams = new ArrayList<>();

    @Transient
    private int tournamentsWon = 0;

    @Transient
    private int matchesWon = 0;

    @Transient
    private int roundsWon = 0;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(@NotBlank final String username, @NotBlank final String password, @Email final String email, final Usergroup usergroup) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.addUsergroup(usergroup);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Usergroup> getUsergroups() {
        return usergroups;
    }

    public void setUsergroups(Set<Usergroup> usergroups) {
        this.usergroups = usergroups;
    }

    public void addUsergroup(Usergroup usergroup) {
        this.usergroups.add(usergroup);
        usergroup.getUsers().add(this);
    }

    public Set<Tournament> getOwnedTournaments() {
        return ownedTournaments;
    }

    public void setOwnedTournaments(final Set<Tournament> ownedTournaments) {
        this.ownedTournaments = ownedTournaments;
    }

    public void addOwnedTournament(Tournament tournament) {
        tournament.setOwner(this);
        this.getOwnedTournaments().add(tournament);
    }

    public Set<Tournament> getJoinedTournaments() {
        return joinedTournaments;
    }

    @PostLoad
    public void postLoad() {
        setJoinedTournaments();
        setStatistics();
    }

    private void setStatistics() {
        this.tournamentsWon = (int) this.joinedTournaments.stream()
                .filter(tournament -> tournament.getStatus().equals(TournamentStatus.Zakończony))
                .filter(tournament -> tournament.getWinner() != null)
                .filter(tournament -> tournament.getWinner().getPlayers().contains(this))
                .count();

        this.matchesWon = this.teams.stream().mapToInt(Team::getWins).sum();
        this.roundsWon = this.teams.stream().mapToInt(Team::getRoundsWin).sum();
    }


    public void setJoinedTournaments() {
        this.joinedTournaments = teams.stream().map(Team::getTournament).collect(Collectors.toSet());
    }

    public void joinToTournament(final Tournament tournament) {
        this.joinedTournaments.add(tournament);
        tournament.getParticipants().add(this);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(final List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
        team.getPlayers().add(this);
    }

    public int getTournamentsWon() {
        return tournamentsWon;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public int getRoundsWon() {
        return roundsWon;
    }
}
