
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TomTom
 */
public class Factorizors {
    public static void main(String[] args) {
        Scanner scanny = new Scanner(System.in);
        System.out.println("Please enter a whole number (no decimals): ");
        int userInput = scanny.nextInt();
        
        int[] factors = findFactors(userInput);
        if(perfect(userInput, factors)){
            System.out.println(userInput + " is a perfect number.");
        }
    }
    
    public static int[] findFactors(int i){
        int[] results = new int[(i/2)];
        int resultsIndex = 0;
        for(int looper = 1; looper <= (i/2); looper++){
            if(i % looper == 0){
                results[resultsIndex] = looper;
                resultsIndex++;
            }
        }
        
        return results;
    }
    
    public static boolean perfect(int userInput, int[] factors){
        int sum = 0;
        for(int i = 0; i < factors.length; i++){
            sum = sum + factors[i];
        }
        if(sum == userInput){
            return true;
        }
        else{
            return false;
        }
    }
}
