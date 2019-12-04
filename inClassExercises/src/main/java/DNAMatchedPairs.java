
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
public class DNAMatchedPairs {
    public static void main(String[] args) {
        String cStrand = "";
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Is this sequence DNA or RNA?");
        String userChoice = scanner.nextLine();
        System.out.println("Please enter the sequence: ");
        String userInput = scanner.nextLine();
        
        userChoice = userChoice.toUpperCase();
        String oStrand = userInput.toUpperCase();
        
        if(userChoice.equals("DNA")){
            for(int i = 0; i < oStrand.length(); i++){
                if(oStrand.charAt(i) == 'G'){
                    cStrand += 'C';
                }
                else if(oStrand.charAt(i) == 'C'){
                    cStrand += 'G';
                }
                else if(oStrand.charAt(i) == 'A'){
                    cStrand += 'T';
                }
                else if(oStrand.charAt(i) == 'T'){
                    cStrand += 'A';
                } 
            }
        System.out.println("The original DNA strand: ");
        System.out.println(oStrand);
        System.out.println("The complimentary DNA strand: ");
        System.out.println(cStrand);
        }
        else if(userChoice.equals("RNA")){
            for(int i = 0; i < oStrand.length(); i++){
                if(oStrand.charAt(i) == 'G'){
                    cStrand += 'C';
                }
                else if(oStrand.charAt(i) == 'C'){
                    cStrand += 'G';
                }
                else if(oStrand.charAt(i) == 'A'){
                    cStrand += 'U';
                }
                else if(oStrand.charAt(i) == 'U'){
                    cStrand += 'A';
                } 
            }
        System.out.println("The original RNA strand: ");
        System.out.println(oStrand);
        System.out.println("The complimentary RNA strand: ");
        System.out.println(cStrand);
        }
    }
}
