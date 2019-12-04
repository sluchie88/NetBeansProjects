/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative1;

import java.util.Random;

/**
 *
 * @author TomTom
 */
public class ComputerPlayer implements Player {

    String compChoice;

    public ComputerPlayer() {

    }

    @Override
    public String getChoice() {
        Random randyBoi = new Random();
        int cChoice = (randyBoi.nextInt(30) + 1);

        if (cChoice <= 10) {
            return "rock";
        } else if (cChoice <= 20) {
            return "paper";
        } else if (cChoice <= 30) {
            return "scissors";
        } else {
            System.out.println("This shouldn't happen");
            return "glue";
        }
    }

}
