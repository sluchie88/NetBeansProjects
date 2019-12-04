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
public class YourLifeInMovies {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int birthYear;
        String userName;
        
        System.out.println("Hi, what's your name?");
        userName = userInput.nextLine();
        
        System.out.println("Okay, " + userName + ", what year were you born?");
        birthYear = userInput.nextInt();
        
        if(birthYear < 1965){
            System.out.println("Did you know the M*A*S*H TV series has been around for almost half a century?");
        }
        else if(birthYear < 1975){
            System.out.println("Have you realized that the original Jurassic Park release is closer to the first moon landing that it is to today?");
        
        }
        else if(birthYear == 1980){
            System.out.println("Did you know Pixar's UP came out half a decade ago?");
            System.out.println("Isn't it crazy to think the first Harry Potter came out 15 years ago?");
            System.out.println("Space Jam came out not last decade, but the decade before THAT.");
        }
        else if(birthYear < 1985){
            System.out.println("Space Jam came out not last decade, but the decade before THAT.");
            
        }
        else if(birthYear < 1995){
            System.out.println("Isn't it crazy to think the first Harry Potter came out 15 years ago?");
            
        }
        else if (birthYear < 2005){
            System.out.println("Did you know Pixar's UP came out half a decade ago?");
        }
        else{
            System.out.println("Get out of here you wihppersnapper.");
        }
    }
}
