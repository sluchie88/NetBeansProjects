package org.g10.lottery.models;

import java.time.LocalDateTime;

public class Ticket {

    private int id;
    private Customer customer;
    private final int[] picks = new int[5];
    private int powerBall;
    private LocalDateTime timestamp = LocalDateTime.now();

    public int getId() {
        return id;
    }

    public Ticket setId(int id) {
        this.id = id;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Ticket setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public int getPickOne() {
        return picks[0];
    }

    public Ticket setPickOne(int pickOne) {
        this.picks[0] = pickOne;
        return this;
    }

    public int getPickTwo() {
        return picks[1];
    }

    public Ticket setPickTwo(int pickTwo) {
        this.picks[1] = pickTwo;
        return this;
    }

    public int getPickThree() {
        return picks[2];
    }

    public Ticket setPickThree(int pickThree) {
        this.picks[2] = pickThree;
        return this;
    }

    public int getPickFour() {
        return picks[3];
    }

    public Ticket setPickFour(int pickFour) {
        this.picks[3] = pickFour;
        return this;
    }

    public int getPickFive() {
        return picks[4];
    }

    public Ticket setPickFive(int pickFive) {
        this.picks[4] = pickFive;
        return this;
    }

    public int getPowerBall() {
        return powerBall;
    }

    public Ticket setPowerBall(int powerBall) {
        this.powerBall = powerBall;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Ticket setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

}
