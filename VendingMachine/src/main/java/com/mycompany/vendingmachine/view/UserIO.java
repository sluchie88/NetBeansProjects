/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.view;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author TomTom
 */
public class UserIO  implements UserIOInterface{
    public UserIO(){
        
    }
    
    Scanner scanny = new Scanner(System.in);

    @Override
    //prints out message passed in
    public void printLine(String message) {
        System.out.println(message);
    }
    
    @Override
    public void print(String msg){
        System.out.print(msg);
    }

    @Override
    //takes in prompt for a double, then returns the double input by user
    //added nextLine() to flush Scanner, just in case
    public double readDouble(String prompt) {
        printLine(prompt);
        double retVal = scanny.nextInt();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for user double, returns double given
    public double readDouble(String prompt, double min, double max) {
        double retVal;
        do {
            printLine(prompt);
            retVal = scanny.nextFloat();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for a float, then returns the float input by user
    //added nextLine() to flush Scanner, just in case
    public float readFloat(String prompt) {
        printLine(prompt);
        float retVal = scanny.nextFloat();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for user Float, returns float given
    public float readFloat(String prompt, float min, float max) {
        float retVal;
        do {
            printLine(prompt);
            retVal = scanny.nextFloat();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for an int, then returns the int input by user
    //added nextLine() to flush Scanner, just in case
    public int readInt(String prompt) {
        printLine(prompt);
        int retVal = scanny.nextInt();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //used a do while to keep looping until a valid input is given
    public int readInt(String prompt, int min, int max) {
        int retVal;
        do {
            printLine(prompt);
            retVal = scanny.nextInt();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for an long, then returns the long input by user
    //added nextLine() to flush Scanner, just in case
    public long readLong(String prompt) {
        printLine(prompt);
        long retVal = scanny.nextInt();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //used a do while to keep looping until a valid input is given
    public long readLong(String prompt, long min, long max) {
        long retVal;
        do {
            printLine(prompt);
            retVal = scanny.nextInt();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt, grabs user input and returns it
    public String readString(String prompt) {
        printLine(prompt);
        String outPut = scanny.nextLine();
        return outPut;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        printLine(prompt);
        BigDecimal outPut = scanny.nextBigDecimal();
        return outPut;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal retVal;
        do{
            printLine(prompt);
            retVal = scanny.nextBigDecimal();
        }while(!(retVal.compareTo(min) >= 0) && !(retVal.compareTo(max) <= 0));
        scanny.nextLine();
        return retVal;
    }
    
}
