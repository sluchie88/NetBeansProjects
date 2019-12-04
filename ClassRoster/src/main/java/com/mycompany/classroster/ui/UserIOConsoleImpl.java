/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classroster.ui;

import java.util.Scanner;

/**
 *
 * @author TomTom
 */
public class UserIOConsoleImpl implements UserIO{
    public UserIOConsoleImpl(){
        
    }
    
    Scanner scanny = new Scanner(System.in);

    @Override
    //prints out message passed in
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    //takes in prompt for a double, then returns the double input by user
    //added nextLine() to flush Scanner, just in case
    public double readDouble(String prompt) {
        print(prompt);
        double retVal = scanny.nextInt();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for user double, returns double given
    public double readDouble(String prompt, double min, double max) {
        double retVal;
        do {
            print(prompt);
            retVal = scanny.nextFloat();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for a float, then returns the float input by user
    //added nextLine() to flush Scanner, just in case
    public float readFloat(String prompt) {
        print(prompt);
        float retVal = scanny.nextFloat();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for user Float, returns float given
    public float readFloat(String prompt, float min, float max) {
        float retVal;
        do {
            print(prompt);
            retVal = scanny.nextFloat();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for an int, then returns the int input by user
    //added nextLine() to flush Scanner, just in case
    public int readInt(String prompt) {
        print(prompt);
        int retVal = scanny.nextInt();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //used a do while to keep looping until a valid input is given
    public int readInt(String prompt, int min, int max) {
        int retVal;
        do {
            print(prompt);
            retVal = scanny.nextInt();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt for an long, then returns the long input by user
    //added nextLine() to flush Scanner, just in case
    public long readLong(String prompt) {
        print(prompt);
        long retVal = scanny.nextInt();
        scanny.nextLine();
        return retVal;
    }

    @Override
    //used a do while to keep looping until a valid input is given
    public long readLong(String prompt, long min, long max) {
        long retVal;
        do {
            print(prompt);
            retVal = scanny.nextInt();
        } while (retVal <= min && retVal >= max);

        scanny.nextLine();
        return retVal;
    }

    @Override
    //takes in prompt, grabs user input and returns it
    public String readString(String prompt) {
        print(prompt);
        String outPut = scanny.nextLine();
        return outPut;
    }
}
