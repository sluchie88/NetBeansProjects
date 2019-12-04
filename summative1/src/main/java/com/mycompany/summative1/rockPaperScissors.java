/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative1;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author TomTom
 */
//only needs to track wins. Win for one is loss for the other
//needs to check for num rounds after restarting
public class rockPaperScissors {

    public static void main(String[] args) {
        Scanner scannyBoi = new Scanner(System.in);
        int someOneWon = 0;
        int pWins = 0;
        int cWins = 0;
        int ties = 0;
        int numRounds = 0;

        int maxWins = maxNumRounds();

        Player compy1 = new ComputerPlayer();

        System.out.println("Is there a human player [1], or two computer players [2]?");
        int players = scannyBoi.nextInt();
        scannyBoi.nextLine();

        //variable for tracking how many wins can accrue before game over
        while (someOneWon != 1 && someOneWon != 2) {
            String pChoice = "";
            String cRPS;
            cRPS = compy1.getChoice();
            
            if (players == 1) {
                Player dumDum = new HumanPlayer(scannyBoi);
                pChoice = dumDum.getChoice();
            } else {
                Player compy2 = new ComputerPlayer();
                pChoice = compy2.getChoice();
            }

            
            //holds value of comp's choice
            //String cRPS = picker();
            //ask if human player or not
            //if computer, make it a computer
            //even if the player types the whole string, this converts it to lower case to make my life easier
            someOneWon = rPS(cRPS, pChoice);

            //checks for tie game, adds to counter to print out
            if (someOneWon == 0) {
                ties++;
                System.out.println("Player wins/losses: " + pWins + "/" + cWins);
                System.out.println("Computer wins/losses: " + cWins + "/" + pWins);
                System.out.println("Tie games: " + ties);
            }
            //checks for player win state. adds one to player wins, one to computer losses
            if (someOneWon == 1) {
                System.out.println("Drat! I picked " + cRPS + ", so " + pChoice + " wins.");
                pWins++;
                System.out.println("Player wins/losses: " + pWins + "/" + cWins);
                System.out.println("Computer wins/losses: " + cWins + "/" + pWins);
                System.out.println("Tie games: " + ties);
                if (pWins < maxWins) {
                    someOneWon = 0;
                }
            }
            //checks for computer win state. adds to computer wins and one to player losses
            if (someOneWon == 2) {
                System.out.println("Ha ha! I won. " + cRPS + ", beats " + pChoice);
                cWins++;
                System.out.println("Player wins/losses: " + pWins + "/" + cWins);
                System.out.println("Computer wins/losses: " + cWins + "/" + pWins);
                System.out.println("Tie games: " + ties);
                if (cWins < maxWins) {
                    someOneWon = 0;
                }
            }
            if ((pWins >= maxWins) ^ (cWins >= maxWins)) {
                String playAgain;

                System.out.println("Would you like to play again? [y/n]");
                playAgain = scannyBoi.nextLine();

                if (playAgain.equalsIgnoreCase("y")) {
                    someOneWon = 0;
                    maxWins = maxNumRounds();
                    pWins = 0;
                    cWins = 0;
                    //reset wins/loss counters
                }
            }
        }

        System.out.println("Thanks for playing!");
    }

    //method for playing rock paper scissors
    //returns 1 if player wins, 2 if computer wins, and 0 for a tie game
    public static int rPS(String compChoice, String userChoice) {

        if (userChoice.equalsIgnoreCase("rock") && compChoice.equals("scissors")) {
            return 1;
        } else if (userChoice.equalsIgnoreCase("paper") && compChoice.equals("rock")) {
            return 1;
        } else if (userChoice.equalsIgnoreCase("scissors") && compChoice.equals("paper")) {
            return 1;
        } //computer win states
        else if (userChoice.equalsIgnoreCase("paper") && compChoice.equals("scissors")) {
            return 2;
        } else if (userChoice.equalsIgnoreCase("rock") && compChoice.equals("paper")) {
            return 2;
        } else if (userChoice.equalsIgnoreCase("scissors") && compChoice.equals("rock")) {
            return 2;
        } //tie game
        else {
            System.out.println("Oh, looks like we have a tie game. I also picked " + compChoice);
            System.out.println("Let's try again, we need a winner.");
            return 0;
        }
    }

    public static String picker() {
        //moved out of main to make repeat games easier
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

    public static int maxNumRounds() {
        Scanner scannyBoi = new Scanner(System.in);
        int numRounds = 0;
        int maxWins;
        boolean valid = true;

        do {
            System.out.println("How many rounds would you like to play? Please pick a number between 1 and 10: ");
            numRounds = scannyBoi.nextInt();
            //clears scanner, was skipping user choice for RPS
            scannyBoi.nextLine();
            //asks player for rounds. Checks if within range, if not resets to 0 and keeps asking
            if (numRounds < 0) {
                System.out.println("That is invalid. Please enter a number between 1 and 10");
            } else if (numRounds > 10) {
                System.out.println("That is invalid. Please enter a number between 1 and 10");
            } else {
                valid = false;
            }
        } while (valid);
        maxWins = ((numRounds / 2) + 1);
        return maxWins;
    }
    /*
    public static String playerTurn(Scanner scannyBoi) {
        String pChoice;
        do {
            System.out.println("Please choose rock, paper, or scissors:");
            pChoice = scannyBoi.nextLine();
        } while (validInput(pChoice));

        return pChoice;

    }
     */
}
