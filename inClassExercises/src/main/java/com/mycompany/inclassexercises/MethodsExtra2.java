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
public class MethodsExtra2 {
    /*
    x2. Write a method that counts the number of vowels in a String.
    X3. Determine if a String starts with a day-of-the-week abbreviation (Mon, Tues, Weds...), Return true if it does.
    X4. Determine if a String starts or ends with a day-of-the-week abbreviation.
    X5. Given a String, remove all numbers (numeric characters) and return the result.
    x6. Given two String: a value and a "tag", return a String that formats the value embedded in a psuedo-HTML tag.
    */
    public static void main(String[] args) {
        String userString = "";
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a string: ");
        userString = userInput.nextLine();
        userString = removeNums(userString);
        System.out.println("Days of the week at the beginning or end? " + dayOfTheWeek(userString));
        System.out.println("Number of vowels: " + vowelCount(userString));
        System.out.println("HTML Tag: " + pseudoMaker("pop", "boom"));
        
    }
    
    //goes through String char by char, checks if it's a number or not
    //replaces numbers with blank spaces and returns
    public static String removeNums(String a){
        for(int i = 0; i < a.length(); i++){
            //System.out.println("for loop");
            char curr = a.charAt(i);
            if(curr == '0' ||
                    curr == '1' ||
                    curr == '2' ||
                    curr == '3' ||
                    curr == '4' ||
                    curr == '5' ||
                    curr == '6' ||
                    curr == '7' ||
                    curr == '8' ||
                    curr == '9'){
                //System.out.println("if statement");
                a = a.replace(curr, ' ');
            }
        }
        System.out.println("String minus nums: " + a);
        return a;
    }
    
    public static boolean dayOfTheWeek(String a){
        String mon = "Mon";
        String tues = "Tues";
        String wed = "Wed";
        String thu = "Thu";
        String fri = "Fri";
        String sat = "Sat";
        String sun = "Sun";
        if(a.startsWith(mon) ||
                a.startsWith(tues) ||
                a.startsWith(wed) ||
                a.startsWith(thu) ||
                a.startsWith(fri) ||
                a.startsWith(sat) ||
                a.startsWith(sun)){
            return true;
        }
        else if (a.endsWith(mon) ||
                a.endsWith(tues) ||
                a.endsWith(wed) ||
                a.endsWith(thu) ||
                a.endsWith(fri) ||
                a.endsWith(sat) ||
                a.endsWith(sun)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static int vowelCount(String a){
        int count = 0;
        for(int i = 0; i < a.length(); i++){
            char curr = a.charAt(i);
            if(curr == 'a' ||
                    curr == 'e' ||
                    curr == 'i' ||
                    curr == 'o' ||
                    curr == 'u' ||
                    curr == 'A' ||
                    curr == 'E' ||
                    curr == 'I' ||
                    curr == 'O' ||
                    curr == 'U' ||
                    curr == 'y' ||
                    curr == 'Y'){
                count++;        
            }
        }
        return count;
    }
    public static String pseudoMaker(String a, String tag){
        if(tag.isEmpty()){
            return a;
        }
        else{
            tag = "<" + tag + ">";
            String html = tag + a + tag;
            return html;
        }
        
    }
}
    

