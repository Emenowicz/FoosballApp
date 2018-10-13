package com.michalowicz.inzynierka.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserModel {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"users"})
    private List<UsergroupModel> usergroups = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(String username, String password, List<UsergroupModel> usergroups) {
        this.username = username;
        this.password = password;
        this.usergroups = usergroups;
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

    public List<UsergroupModel> getUsergroups() {
        return usergroups;
    }

    public void setUsergroups(List<UsergroupModel> usergroups) {
        this.usergroups = usergroups;
    }

    public void addUsergroup(UsergroupModel usergroupModel){
        this.usergroups.add(usergroupModel);
        usergroupModel.getUsers().add(this);
    }

}
