package com.michalowicz.inzynierka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UsergroupModel {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"usergroups"})
    private Set<UserModel> users = new HashSet<>();

    public UsergroupModel() {
    }

    public UsergroupModel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UserModel> users) {
        this.users = users;
    }

    public void addUser(UserModel user) {
        this.users.add(user);
        user.getUsergroups()
                .add(this);
    }
}
