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
public class Palindrome {

    public static void main(String[] args) {
        String userString = "";
        Scanner scanBoi = new Scanner(System.in);
        System.out.println("Please enter a string to see if it's a plaindrome: ");
        userString = scanBoi.next();
        
        boolean isAPally = palindrome(userString);
        if(isAPally){
            System.out.println("Yes, " + userString + " is a palindrome.");
        }
        else{
            System.out.println("No, " + userString + " is not a palindrome.");
        }
    }
    
    public static boolean palindrome(String s){
        String reversed = "";
        
        for(int i = (s.length() - 1); i >= 0; i--){
            reversed = reversed + s.charAt(i);
        }
        if(reversed.equalsIgnoreCase(s)){
            return true;
        }else{
            return false;
        }
    }

}
