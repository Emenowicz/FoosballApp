package com.michalowicz.inzynierka.dto;

import com.michalowicz.inzynierka.entity.RuleSet;

public class CreateTournamentForm {
    private String name;
    private RuleSet ruleSet;
    private int teamsNeeded;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(final RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public int getTeamsNeeded() {
        return teamsNeeded;
    }

    public void setTeamsNeeded(final int teamsNeeded) {
        this.teamsNeeded = teamsNeeded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
