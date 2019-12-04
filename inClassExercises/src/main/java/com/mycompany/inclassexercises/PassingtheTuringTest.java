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
public class PassingtheTuringTest {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        String userName, aiName, userColor, aiColor, userFood, aiFood; 
        int userNumber, aiNumber;
        aiName = "Josie";
        aiColor = "orange";
        aiFood = "human food";
    
        
        System.out.println("Hello there!");
        System.out.println("What is your name?");
        userName = userInput.nextLine();
        
        System.out.println("Nice to meet you, " + userName + ". My name is " + aiName);
        System.out.println(userName + ", what's your favorite color?");
        userColor = userInput.nextLine();
        
        System.out.println("Oh, " + userColor + " is pretty great, but I like " + aiColor + " more.");
        
        System.out.println("Do you have a favorite number?");
        userNumber = userInput.nextInt();
        aiNumber = userNumber;
        userInput.nextLine();
        System.out.println("No way, my lucky number is " + aiNumber + " too!");
        
        System.out.println("What is your favorite food?");
        userFood = userInput.nextLine();
        System.out.println("I will have to try that. My favorite food is " + aiFood);
        System.out.println("Nice chatting with you. Goodbye!");
    }
}
