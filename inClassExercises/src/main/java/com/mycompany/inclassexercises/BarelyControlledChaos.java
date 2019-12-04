/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.inclassexercises;

import java.util.Random;

/**
 *
 * @author TomTom
 */
public class BarelyControlledChaos {
    public static void main(String[] args) {
        String color = randomColor();
        String animal = randomAnimal();
        int weight = randomNum(5, 200);
        int distance = randomNum(10, 20);
        int number = randomNum(1000, 2000);
        int time = randomNum(2, 6);
        
        System.out.println("Once, when I was very small...");
        System.out.println("I was chased by a " + color + ", " + weight + "lb miniature " + animal + " for over " + distance + " miles!!");
        System.out.println("I had to hide in a field of over " + number + " " + randomColor() + " poppies for nearly " + time + " hours until it left me alone!");
        System.out.println("\nIt was QUITE the experience, let me tell you!");
        /*System.out.println("Color: " + color);
        System.out.println("Animal: " + animal);
        System.out.println("Weight: " + weight);
        System.out.println("Distance: " + distance);
        System.out.println("Number: " + number);
        System.out.println("Time: " + time);
*/
    }
    
    public static int randomNum(int min, int max){
        Random Randy = new Random();
        //subtract min from max, then add back to keep in range
        //add one because upper bound is not inclusive
        int num = Randy.nextInt((max - min + 1)) + min;
        return num;
    }
    public static String randomAnimal(){
        Random Randier = new Random();
        int choice = Randier.nextInt(4);
        if(choice == 0){
            return "Lion";
        }
        else if(choice == 1){
            return "Tiger";
        }
        else if(choice == 2){
            return "Meerkat";
        }
        else if(choice == 3){
            return "Hedgehog";
        }
        else{
            return "Doggo";
        }
    }
    public static String randomColor(){
        Random Randiest = new Random();
        int choice = Randiest.nextInt(4);
        if(choice == 0){
            return "Orange";
        }
        else if(choice == 1){
            return "Blue";
        }
        else if(choice == 2){
            return "Yellow";
        }
        else if(choice == 3){
            return "Red";
        }
        else{
            return "Pink";
        }
    }
}
