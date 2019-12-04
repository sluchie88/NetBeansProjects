
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
public class ReverseEngineer {
    public static void main(String[] args) {
        int age = 0;
        Scanner scannyBoi = new Scanner(System.in);
        System.out.println("How old are you?");
        age = scannyBoi.nextInt();
        
        System.out.println("Now it's time for a magic trick. Can you guess my math?");
        System.out.println(ohohItsMagic(age));
        System.out.println("Viola! The first two digits are your age, the second two are your shoe size.");
        System.out.println("Cool, right?");
    }
    
    public static int ohohItsMagic(int age){
        Scanner scanner = new Scanner(System.in);
        int shoeSize;
        int date;
        
        System.out.println("First we'll multiply by 5, then add a zero. We get: ");
        age = age * 5;
        age = age * 10;
        System.out.println(age);
        System.out.println("Now, what is today's date?");
        date = scanner.nextInt();
        
        System.out.println("Let's add that to our current number. Then double that.");
        age += date;
        age = age * 2;
        
        System.out.println("What is your shoe size? (whole numbers only please)");
        shoeSize = scanner.nextInt();
        age = age + shoeSize;
        
        System.out.println("This last step is the mystery");
        System.out.println("It works for any day of the month, but only if you do it twice.");
        age = age - (date * 2);
        
        return age;
    }
}
