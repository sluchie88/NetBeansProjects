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
public class Opinionator {
    public static void main(String[] args) {
        Random randomizer = new Random();
        System.out.println("I can't decide what animal I like the best.");
        System.out.println("I know! Random can decide FOR me!");
        
        int x = randomizer.nextInt(5);
        System.out.println("The number chosen was: " + x);
         
        if(x == 0){
            System.out.println("Llamas are the best!");
        } else if(x == 1){
            System.out.println("Dodos are the best!");
        } else if(x == 2){
            System.out.println("Woolly Mammoths are DEFINITELY the best!");
        } else if(x == 3){
            System.out.println("Sharks are the greatest, they have their own week.");
        } else if(x == 4){
            System.out.println("Cockatoos are just so awesomme!");
        } else if(x == 5){
            System.out.println("Have you ever met a mole-rat? They're GREAT!");
        }
        
        System.out.println("Thanks random. You rock!");
    }
}
