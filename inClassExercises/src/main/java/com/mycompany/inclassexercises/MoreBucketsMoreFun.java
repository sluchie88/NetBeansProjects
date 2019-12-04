/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.inclassexercises;

/**
 *
 * @author TomTom
 */
public class MoreBucketsMoreFun {
    public static void main(String[] args) {
        //can declare all vars at the beginning of the program
        int butterflies, beetles, bugs;
        String color, size, shape, thing;
        double number;
        
        butterflies = 2;
        beetles = 4;
        
        bugs = butterflies + beetles;
        System.out.println("There are only " + butterflies + "butterflies,");
        System.out.println("but " + bugs + " bugs total.");
        System.out.println("Uh oh, my dog ate one.");
        butterflies--;
        System.out.println("Now there are only " + butterflies + " butterflies left");
        System.out.println("But still " + bugs + " bugs left, wait a minute!!!");
        System.out.println("Maybe I just counted wrong the first time...");
                
    }
}
