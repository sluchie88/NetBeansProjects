
import static java.lang.System.out;
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
public class MarcusProg {
    public static void main(String[] args) {
        String userInput = "";
        String reversed = "";
        Scanner scanBoi = new Scanner(System.in);
        System.out.println("Please enter a string:");
        userInput = scanBoi.nextLine();
        
        
        for(int i = (userInput.length() - 1); i >= 0; i--){
            reversed += userInput.charAt(i);
        }
        
        System.out.println("Your string, reversed: ");
        System.out.println(reversed);
    }
}
