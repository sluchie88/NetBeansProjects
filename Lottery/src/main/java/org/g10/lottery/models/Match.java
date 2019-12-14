package org.g10.lottery.models;

public class Match {

    /*
    class for holding matching tickets
    I added an overridden toString() method for printing
    the matching tickets.
    Orignally I was printing all of the ticket info with some other
    stuff but it was too much gobbledygook. This toString is nicer
    and works well with my stream implementation
    */
    
    private final Ticket ticket;
    private int numberMatchs;
    private boolean powerBallMatch;
    private int amountWon;

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

    public int getAmountWon() {
        return amountWon;
    }

    public void setAmountWon(int amountWon) {
        this.amountWon = amountWon;
    }

    @Override
    public String toString() {
        String matchString = "Email: " + this.ticket.getCustomer().getEmailAddress() + ".\n";
        matchString += "Amount won: $" + Integer.toString(this.amountWon);
        //amountWon += ticket.toString();
        matchString += ". Total matches: " + Integer.toString(this.numberMatchs);
        return matchString;
    }
}
