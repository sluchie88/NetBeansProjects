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
public class BestAdderEver {
    public static void main(String[] args) {
        String name, food;
        int age, ageLearnedToWhistle;
        boolean ateGnocchi;
        Scanner userInput = new Scanner(System.in);
        
        
        
        System.out.println("What is your name?");
        name = userInput.nextLine();
        
        System.out.println("What is your favorite food? ");
        food = userInput.nextLine();
        
        System.out.println("How old are you?");
        age = userInput.nextInt();
        
        System.out.println("How old were you when you learned to whistle?");
        ageLearnedToWhistle = userInput.nextInt();
        
        System.out.println("Is this true or false: You have eaten Gnocchi.");
        ateGnocchi = userInput.nextBoolean();
        
        
        System.out.println("My name is " + name + ". I am " + age + " years old.");
        System.out.println("I was " + ageLearnedToWhistle + " years old when I learned to whistle.");
        System.out.println("It would be " + ateGnocchi + " to say I have eaten Gnocchi.");
        /*
        name = "Thomas";
        food = "Spaghetti and meatballs";
        age = 31;
        ageLearnedToWhistle = 5;
        ateGnocchi = true;
        
        System.out.println(name);
        System.out.println("Favorite food: " + food);
        System.out.println("Age: " + age);
        System.out.println("I learned to whistle when I was " + ageLearnedToWhistle);
        System.out.println("If I said I hadn't eaten Gnocchi it would be un" + ateGnocchi);
        */
    } 
}
