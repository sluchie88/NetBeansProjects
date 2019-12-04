/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.inclassexercises;

import java.util.Scanner;

/**
 *
 * @author TomTom
 */
public class GuessMe {
    public static void main(String[] args) {
        int pickedNum = 56;
        int userGuess;
        Scanner userInput = new Scanner(System.in);
        System.out.println("I've chosen a number, bet you can't guess it!");
        System.out.println("What do you think it is?");
        userGuess = userInput.nextInt();
        
        if(userGuess == pickedNum){
            System.out.println("Wow, nice guess! That was it!");
        } else if(userGuess < pickedNum){
            System.out.println("Ha, nice try - too low! I chose " + pickedNum);
        }
        else{
            System.out.println("Too bad, way too high. I chose " + pickedNum);
        }
    }
}
