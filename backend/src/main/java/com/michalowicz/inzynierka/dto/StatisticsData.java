package com.michalowicz.inzynierka.dto;

public class StatisticsData {
    private int tournamentsWon;
    private int tournamentsPlayed;
    private int matchesWon;
    private int matchesPlayed;
    private int roundsWon;
    private int roundsPlayed;
    private int pointsScored;
    private double matchWinRatio;
    private String mostPlayedWith;

    public int getTournamentsWon() {
        return tournamentsWon;
    }

    public void setTournamentsWon(final int tournamentsWon) {
        this.tournamentsWon = tournamentsWon;
    }

    public int getTournamentsPlayed() {
        return tournamentsPlayed;
    }

    public void setTournamentsPlayed(final int tournamentsPlayed) {
        this.tournamentsPlayed = tournamentsPlayed;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(final int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(final int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(final int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(final int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public int getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(final int pointsScored) {
        this.pointsScored = pointsScored;
    }

    public double getMatchWinRatio() {
        return matchWinRatio;
    }

    public void setMatchWinRatio(final double matchWinRatio) {
        this.matchWinRatio = matchWinRatio;
    }

    public String getMostPlayedWith() {
        return mostPlayedWith;
    }

    public void setMostPlayedWith(final String mostPlayedWith) {
        this.mostPlayedWith = mostPlayedWith;
    }
}
