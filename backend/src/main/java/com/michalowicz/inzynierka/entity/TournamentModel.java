package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TournamentModel {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status = TournamentStatus.Otwarty;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timeCreated = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"ownedTournaments","joinedTournaments"})
    private UserModel owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"joinedTournaments","ownedTournaments"})
    private Set<UserModel> participants = new HashSet<>();

    public TournamentModel() {
    }

    public TournamentModel(@NotBlank final String name) {
        this.name = name;
    }

    public TournamentModel(@NotBlank final String name, final UserModel owner) {
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

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(final UserModel owner) {
        this.owner = owner;
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

    public Set<UserModel> getParticipants() {
        return participants;
    }

    public void setParticipants(final Set<UserModel> participants) {
        this.participants = participants;
    }

    public void addParticipant(final UserModel user){
        this.participants.add(user);
        user.getJoinedTournaments().add(this);
    }
}
