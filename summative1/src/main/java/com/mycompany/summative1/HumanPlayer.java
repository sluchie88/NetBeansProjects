/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative1;

import static java.lang.System.out;
import java.util.Scanner;

/**
 *
 * @author TomTom
 */
public class HumanPlayer implements Player {

    Scanner scanner;
    String playerChoice;

    public HumanPlayer(Scanner s) {
        scanner = s;
    }

    @Override
    public String getChoice() {
        do {
            System.out.println("Please choose rock, paper, or scissors: ");
            playerChoice = scanner.nextLine();
        } while (!validInput(playerChoice));
        return playerChoice;
    }

    public static boolean validInput(String userInput) {
        if (userInput.equalsIgnoreCase("rock")) {
            return true;
        } else if (userInput.equalsIgnoreCase("paper")) {
            return true;
        } else if (userInput.equalsIgnoreCase("scissors")) {
            return true;
        } else {
            System.out.println("That is not a valid input.");
            return false;
        }
    }
}
