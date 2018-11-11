package com.michalowicz.inzynierka.entity;

public enum TournamentStatus {
    Otwarty ("Otwarty"),
    Trwający ("W trakcie"),
    Zakończony ("Zakończony");

    private final String name;

    TournamentStatus(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
