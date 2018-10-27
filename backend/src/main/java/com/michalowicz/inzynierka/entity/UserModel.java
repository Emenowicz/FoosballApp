package com.michalowicz.inzynierka.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserModel {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    @Lob
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"users"})
    private Set<UsergroupModel> usergroups = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"owner"})
    private Set<TournamentModel> ownedTournaments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"participants"})
    private Set<TournamentModel> joinedTournaments = new HashSet<>();

    public UserModel() {
    }

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    public Set<UsergroupModel> getUsergroups() {
        return usergroups;
    }

    public void setUsergroups(Set<UsergroupModel> usergroups) {
        this.usergroups = usergroups;
    }

    public void addUsergroup(UsergroupModel usergroupModel) {
        this.usergroups.add(usergroupModel);
        usergroupModel.getUsers().add(this);
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Set<TournamentModel> getOwnedTournaments() {
        return ownedTournaments;
    }

    public void setOwnedTournaments(final Set<TournamentModel> ownedTournaments) {
        this.ownedTournaments = ownedTournaments;
    }

    public void addOwnedTournament(TournamentModel tournamentModel) {
        tournamentModel.setOwner(this);
        this.getOwnedTournaments().add(tournamentModel);
    }

    public Set<TournamentModel> getJoinedTournaments() {
        return joinedTournaments;
    }

    public void setJoinedTournaments(final Set<TournamentModel> joinedTournaments) {
        this.joinedTournaments = joinedTournaments;
    }

    public void joinToTournament(final TournamentModel tournament) {
        this.joinedTournaments.add(tournament);
        tournament.getParticipants().add(this);
    }
}
