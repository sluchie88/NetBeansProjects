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
public class MethodsExtra1 {
    //1. Write a method that takes two values: a String and a number of times to
    //repeat. The method should return the value repeated the appropriate number 
    //of times.
    public static void main(String[] args) {
        String userString = "";
        int userRepeats;
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a string: ");
        userString = userInput.nextLine();
        System.out.println("Enter how many times it should repeat: ");
        userRepeats = userInput.nextInt();
        letsRepeat(userString, userRepeats);
    }
    
    public static void letsRepeat(String a, int repeats){
       int counter = 0;
        while(repeats > 0){
           System.out.print(a);
           repeats--;
           counter++;
           if((counter % 10) == 0){
               System.out.println("");
           }
       } 
       return;
    }
}
