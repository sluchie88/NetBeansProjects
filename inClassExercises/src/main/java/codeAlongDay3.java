
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
public class codeAlongDay3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do{
            printGrid(10, 10, '@', '-');
            System.out.println("Play again?");
        } while(scanner.nextLine().equalsIgnoreCase("y"));
    } 
    
    private static void printGrid(int rows, int cols, char border, char inner){
        for (int row = 0; row < 4; row++){
                char toPrint;
                if(row == 0 || row == 3 || col == 0 || col == 3){
                    toPrint = border;
                } else {
                    toPrint = inner;
                }
                System.out.print(toPrint);
            }
            System.out.println("");
            }
    }
    
}
