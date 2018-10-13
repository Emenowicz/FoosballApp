package com.michalowicz.inzynierka.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"users"})
    private Set<UsergroupModel> usergroups = new HashSet<>();

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

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

    public void addUsergroup(UsergroupModel usergroupModel){
        this.usergroups.add(usergroupModel);
        usergroupModel.getUsers().add(this);
    }

}
