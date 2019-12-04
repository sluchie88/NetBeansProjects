/*
 * To change this license header, choose License Headers in Project Properties.
/ * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.summative1;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Thomas Sluciak
 */
public class Connect4 {

    public static void main(String[] args) {
        int[][] gameBoard = new int[7][7];
        //set to zero. if 1, player won. if 2, computer won
        int turns = 0;
        int pOneWins = 0;
        int pOneLoss = 0;
        int pTwoWins = 0;
        int pTwoLoss = 0;

        boolean twoPlayers = areThereTwoPlayers();

        String compName = "Shallow Blue";
        String pOne = playerOneName();
        String pTwo = "";

        if (twoPlayers) {
            pTwo = playerTwoName();
        } else {
            System.out.println("Hello, " + pOne + ", my name is " + compName);
        }

        boolean pOneTurn = playerOneGoesFirst(twoPlayers);

        //loops to continue playing until game is won
        //there are 49 possible turns (7 x 7). If there are that many turns
        //and no winner is declared, will ask if they want to restart
        while (turns < 50) {
            printBoard(gameBoard);
            /*
                calls to method for player two to take their turn
                checks for 4 in a row using crawler. if a win is found
                increments player 2 wins, and player one losses.
                Calls to method to check if they want to play again. If yes,
                resets the board and turn counter to keep playing. If no,
                breaks the while loop and exits.
             */
            if (pOneTurn) {
                gameBoard = playerOneTurn(gameBoard, pOne);
                pOneTurn = false;
                //use this if to kill game and allow for a restart
                if (crawler(gameBoard, 1) == true) {
                    printBoard(gameBoard);
                    pOneWins++;
                    pTwoLoss++;
                    System.out.println("");
                    System.out.println(pOne + " wins!");
                    System.out.println("");
                    System.out.println(pOne + " wins/losses: " + pOneWins + "/" + pOneLoss);
                    if (twoPlayers) {
                        System.out.println(pTwo + " wins/losses: " + pTwoWins + "/" + pTwoLoss);
                    }
                    if (!twoPlayers) {
                        System.out.println(compName + " wins/losses: " + pTwoWins + "/" + pTwoLoss);
                    }
                    if (endGame()) {
                        gameBoard = new int[7][7];
                        turns = 0;
                        pOneTurn = playerOneGoesFirst(twoPlayers);
                    } else {
                        turns = 100;
                    }
                }
                //executes computer turn when there are not two players present,
                //and it is not player one's turn. Will never execute when two players
                //are present
            } else if (!pOneTurn && !twoPlayers) {
                //used if only one human player is present
                gameBoard = compTurn(gameBoard);
                pOneTurn = true;

                if (crawler(gameBoard, 2) == true) {
                    printBoard(gameBoard);
                    pTwoWins++;
                    pOneLoss++;
                    System.out.println("");
                    System.out.println(compName + " wins!");
                    System.out.println("");
                    System.out.println(pOne + " wins/losses: " + pOneWins + "/" + pOneLoss);
                    System.out.println(compName + " wins/losses: " + pTwoWins + "/" + pTwoLoss);
                    if (endGame()) {
                        gameBoard = new int[7][7];
                        turns = 0;
                        pOneTurn = playerOneGoesFirst(twoPlayers);
                    } else {
                        turns = 100;
                    }
                }
            } else {
                //used if a second human player is present
                gameBoard = playerTwoTurn(gameBoard, pTwo);
                pOneTurn = true;

                if (crawler(gameBoard, 2) == true) {
                    printBoard(gameBoard);
                    pTwoWins++;
                    pOneLoss++;
                    System.out.println("");
                    System.out.println(pTwo + " wins!");
                    System.out.println("");
                    System.out.println(pOne + " wins/losses: " + pOneWins + "/" + pOneLoss);
                    System.out.println(pTwo + " wins/losses: " + pTwoWins + "/" + pTwoLoss);
                    if (endGame()) {
                        gameBoard = new int[7][7];
                        turns = 0;
                        pOneTurn = playerOneGoesFirst(twoPlayers);
                    } else {
                        turns = 100;
                    }
                }
            }
            turns++;
        }
        System.out.println("Goodbye.");
        //Simple if statements for winning player message
        //make a function for playing again that returns a boolean
    }

    /*
    My original version of crawler was a huge mess. Tried to make it recursive, check in 8 directions,
    and return the value needed. I tried checking based on the most recent token dropped, but realized that
    wouldn't work because it wouldn't necessarily end up at the start or end of the row, but could be the middle.
    
    The long and short is, it became massive and unwieldy and bug-ridden. So After 2 hours of frustration I deleted it
    and went back tot the drawing board (and took a peek online, but did not copy code). I realized I should start
    from the bottom of the board, work my way right and go up row by row. That way I would only need to check if I ran
    into the correct player token, only check in 4 directions, and could move the row checking methods
    out of crawler and make them recursive.
    
    I know this is more story than comment, but I feel relieved, proud, and more than a little frustrated 
    after trying to get this thing up and running. I needed someone else to know and bask in my glory.
     */
    public static boolean crawler(int[][] array, int player) {
        boolean rowFound = false;
        for (int x = (array.length - 1); x > 0; x--) {
            for (int y = 0; y < array[x].length; y++) {
                if (array[x][y] == player) {
                    if (checkUp(array, x, y, player, 1) >= 4) {
                        rowFound = true;
                    } else if (checkLeftDiag(array, x, y, player, 1) >= 4) {
                        rowFound = true;
                    } else if (checkRight(array, x, y, player, 1) >= 4) {
                        rowFound = true;
                    } else if (checkRightDiag(array, x, y, player, 1) >= 4) {
                        rowFound = true;
                    }
                }
            }
        }
        return rowFound;
    }

    //checks spaces above for pieces found for the player. Continues traveling up if found. Terminates if not
    public static int checkUp(int[][] array, int x, int y, int player, int inARow) {
        while ((x - 1) > 0 && array[(x - 1)][y] == player) {
            inARow += 1;
            x -= 1;
        }
        return inARow;
    }

    //checks spaces to the right for pieces found for the player. Continues traveling up if found. Terminates if not
    public static int checkRight(int[][] array, int x, int y, int player, int inARow) {
        while ((y + 1) < array.length && array[x][(y + 1)] == player) {
            inARow += 1;
            y += 1;
        }
        return inARow;
    }

    //checks spaces above and to the left for pieces found for the player. Continues traveling up if found. Terminates if not
    public static int checkLeftDiag(int[][] array, int x, int y, int player, int inARow) {
        while ((x - 1) >= 0 && (y - 1) >= 0
                && array[(x - 1)][(y - 1)] == player) {
            inARow += 1;
            x -= 1;
            y -= 1;
        }
        return inARow;
    }

    //checks spaces above and to the right for pieces found for the player. Continues traveling up if found. Terminates if not
    public static int checkRightDiag(int[][] array, int x, int y, int player, int inARow) {
        //loop for checking the right diagonal (up and right) from the current spot
        while ((x - 1) > 0 && (y + 1) < array.length
                && array[(x - 1)][(y + 1)] == player) {
            inARow += 1;
            x -= 1;
            y += 1;
        }
        return inARow;
    }

    //initially takes in user's desired column. checks to make sure it's valid using a validate method, then places piece in correct spot
    //had an issue where it catches an invalid input, but any input after that crashes the program. I think this had something to do with
    //using a recursive call on this function. Moving the validate function to a separate method fixed the issue, but still curious why it happened
    public static int[][] playerOneTurn(int[][] array, String name) {
        Scanner scannyBoi = new Scanner(System.in);
        int picked = 10;
        //change to do while, take in input before printing out invalid input message
        while (invalidInput(picked)) {
            System.out.println(name + ", which column would you like to put your piece in? [1 to 7]");
            picked = (scannyBoi.nextInt() - 1);
        }

        //used a for loop. starts at bottom of specified column and moves up until it finds an open space,
        //an open space here being defined as a zero
        //added the boolean placed because it was filling the entire column, rather than one spot and seemed the easiest fix
        boolean placed = false;
        for (int i = (array.length - 1); i > 0 && placed == false; i--) {
            if (array[i][picked] == 0) {
                array[i][picked] = 1;
                placed = true;
            } //additional check for a full column. Gives player back their turn instead of skipping
            else if (i == 1 && array[i][picked] != 0) {
                System.out.println("That column is already full, " + name + ". Please choose another.");
                printBoard(array);
                playerOneTurn(array, name);
            } else if (i == 0) {
                playerOneTurn(array, name);
            }
        }

        return array;
    }

    //initially takes in user's desired column. checks to make sure it's valid using a validate method, then places piece in correct spot
    //had an issue where it catches an invalid input, but any input after that crashes the program. I think this had something to do with
    //using a recursive call on this function. Moving the validate function to a separate method fixed the issue, but still curious why it happened
    public static int[][] playerTwoTurn(int[][] array, String name) {
        Scanner scannyBoi = new Scanner(System.in);
        int picked = 10;
        while (invalidInput(picked)) {
            System.out.println(name + ", which column would you like to put your piece in? [1 to 7]");
            picked = (scannyBoi.nextInt() - 1);
        }
        //used a for loop. starts at bottom of specified column and moves up until it finds an open space,
        //an open space here being defined as a zero
        //added the boolean placed because it was filling the entire column, rather than one spot and seemed the easiest fix
        boolean placed = false;
        for (int i = (array.length - 1); i > 0 && placed == false; i--) {
            if (array[i][picked] == 0) {
                array[i][picked] = 2;
                placed = true;
            } //additional check for a full column. Gives player back their turn instead of skipping
            else if (i == 1 && array[i][picked] != 0) {
                System.out.println("That column is already full, " + name + ". Please choose another.");
                printBoard(array);
                playerTwoTurn(array, name);
            } else if (i == 0) {
                playerTwoTurn(array, name);
            }
        }

        return array;
    }

    //places computer's pieces randomly. I want to try and make it deliberate rather than random,
    //but it's Sunday afternoon and I want to go outside. 
    //All work and no play makes Homer something something
    public static int[][] compTurn(int[][] array) {
        Random rand = new Random();
        int cChoice = (rand.nextInt(7000) + 1);
        int col = 0;
        if (cChoice <= 1000) {
            col = 0;
        } else if (cChoice > 1000 && cChoice <= 2000) {
            col = 1;
        } else if (cChoice > 2000 && cChoice <= 3000) {
            col = 2;
        } else if (cChoice > 3000 && cChoice <= 4000) {
            col = 3;
        } else if (cChoice > 4000 && cChoice <= 5000) {
            col = 4;
        } else if (cChoice > 5000 && cChoice <= 6000) {
            col = 5;
        } else if (cChoice > 6000 && cChoice <= 7000) {
            col = 6;
        }

        boolean placed = false;
        for (int i = (array.length - 1); i > 0 && placed == false; i--) {
            if (array[i][col] == 0) {
                array[i][col] = 2;
                placed = true;
                System.out.println((col + 1) + " seems like a good spot. Your turn.");
                System.out.println("");
                System.out.println("");
            } //additional check for a full column. Gives player back their turn instead of skipping
            else if (i <= 1 && array[i][col] != 0) {
                printBoard(array);
                compTurn(array);
            } else if (i == 0) {
                compTurn(array);
            }
        }
        return array;
    }

    //prints out game board. For index 0, 0, prints as column numbers
    //if a number 1 is there, swaps in X for player piece
    //if a 2 is present, swaps in O for computer piece
    //if a 0, prints out an _
    public static void printBoard(int[][] array) {
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array.length; y++) {
                if (x == 0) {
                    System.out.print((y + 1) + " ");
                } else if (array[x][y] == 1) {
                    System.out.print("X ");
                } else if (array[x][y] == 2) {
                    System.out.print("O ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println("");
        }
    }

    //checks if there are two human players or not
    public static boolean areThereTwoPlayers() {
        Scanner sBoi = new Scanner(System.in);
        String answer = "";
        boolean twoPlayers = false;

        System.out.println("Are there two players? [y/n]");
        System.out.println("Enter n to play the computer.");
        answer = sBoi.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            twoPlayers = true;
        }
        return twoPlayers;
    }

    //grabs player one's name
    public static String playerOneName() {
        String name = "";
        Scanner sBoi = new Scanner(System.in);
        System.out.println("Player one, what is your name?");
        name = sBoi.nextLine();

        return name;
    }

    //grabs player two's name
    public static String playerTwoName() {
        String name = "";
        Scanner sBoi = new Scanner(System.in);
        System.out.println("Player two, what is your name?");
        name = sBoi.nextLine();

        return name;
    }

    //checks if user wants to play again
    //returns true if user wants to play again, false if not
    //returns a boolean because it will trigger if statements in main to
    //reset the game or break the while loop and end the program
    public static boolean endGame() {
        Scanner scanBoi = new Scanner(System.in);
        boolean playAgain = false;
        String answer = "";
        System.out.println("Would you like to play again? [y/n]");
        answer = scanBoi.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            playAgain = true;
        }
        return playAgain;
    }

    //takes user input. returns false if player two will go first,
    //returns true if player one will go first. Used to set the boolean in main
    //that determines which player's turn it is, both initially and after restarting the game
    public static boolean playerOneGoesFirst(boolean twoPlayers) {
        int startingPlayer;
        Scanner scanner = new Scanner(System.in);
        boolean pOneTurn = true;
        System.out.println("Who will go first? Please enter the player's number [1/2]");
        if (!twoPlayers) {
            System.out.println("If you want the computer to go first, enter 2.");
        }

        startingPlayer = scanner.nextInt();
        //clears scanner for next input
        scanner.nextLine();
        if (startingPlayer == 2) {
            pOneTurn = false;
        }
        return pOneTurn;
    }

    //returns true if input is invalid and false if it's valid
    //moving this to a separate method fixed my issue with recursive calls
    public static boolean invalidInput(int pChoice) {
        //validates input. Did this recursively rather than making a separate function.
        boolean invalid = false;
        if (pChoice < 0 || pChoice > 7) {
            System.out.println("Please enter a number between 1 and 7. " + pChoice + " is not a valid input.");
            invalid = true;
        }
        return invalid;
    }
}
