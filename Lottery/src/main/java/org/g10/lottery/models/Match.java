package org.g10.lottery.models;

public class Match {

    private final Ticket ticket;
    private int numberMatchs;
    private boolean powerBallMatch;

    public Match(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public int getNumberMatchs() {
        return numberMatchs;
    }

    public void setNumberMatchs(int numberMatchs) {
        this.numberMatchs = numberMatchs;
    }

    public boolean isPowerBallMatch() {
        return powerBallMatch;
    }

    public void setPowerBallMatch(boolean powerBallMatch) {
        this.powerBallMatch = powerBallMatch;
    }

}
