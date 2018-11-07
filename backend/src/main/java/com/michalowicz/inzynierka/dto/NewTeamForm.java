package com.michalowicz.inzynierka.dto;

public class NewTeamForm {
    private String name;
    private String password;
    private String privacy;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(final String privacy) {
        this.privacy = privacy;
    }
}
