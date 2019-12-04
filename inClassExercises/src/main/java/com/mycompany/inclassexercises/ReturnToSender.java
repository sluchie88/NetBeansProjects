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
public class ReturnToSender {
    public static void main(String[] args) {
        char aMystery = mystery();
        String totallyUnexpected = unexpected();
        double aSurprise = surprise();
        boolean itsClassified = classified();
        int aSecret = secret();
        
        System.out.println("The methods have returned! Their results...\n");
        System.out.println("Mysterious: " + aMystery);
        System.out.println("Unexpected: " + totallyUnexpected);
        System.out.println("Surprise: " + aSurprise);
        System.out.println("Classified: " + itsClassified);
        System.out.println("Secret: " + aSecret);
    }
    
    public static int secret(){
        return 42;
    }
    public static double surprise(){
        return 3.14;
    }
    public static char mystery(){
        return 'X';
    }
    public static boolean classified(){
        return true;
    }
    public static String unexpected(){
        return "Spanish Inquisition";
    }
}
