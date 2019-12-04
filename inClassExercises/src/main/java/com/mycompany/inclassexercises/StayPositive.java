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
public class StayPositive {
    public static void main(String[] args) {
       Scanner userInput = new Scanner(System.in);
       int cowntDown;
       System.out.println("Please give me a non-negative number: ");
       cowntDown = userInput.nextInt();
       System.out.println("Here we go, hold onto your butt!");
       while(cowntDown > 0){
           System.out.println(cowntDown);
           --cowntDown;
       }
        System.out.println("Whew, better stop there..!");
    }
    
    
}
