
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
public class InterestCalculator {

    public static void main(String[] args) {
        Scanner scannyBoi = new Scanner(System.in);
        System.out.println("What is the balance of your account? ");
        double acctBalance = scannyBoi.nextDouble();
        System.out.println("What is the interest rate?");
        double acctInterest = scannyBoi.nextDouble();
        int years = 0;
        System.out.println("How many years will it stay in the account?");
        years = scannyBoi.nextInt();
        
        /*System.out.println("");
        System.out.println("Is the interest compunded quarterly, monthly, or daily?");
        String cmpdStr = scannyBoi.nextLine();
        int cmpdFreq = 0;
        switch cmpdStr{
            case "quarterly":
                cmpdFreq = 4;
                break;
            case "monthly":
                cmpdFreq = 12;
                break;
            case "daily":
                cmpdFreq = 365;
                break;
                
        } */
        double finalTot = interestQuarterly(acctBalance, acctInterest, years);
        double accruedInt = finalTot - acctBalance;
        
        System.out.println("After " + years + " years, your $" + acctBalance + " will have become $" + finalTot);
        System.out.println("The total interest accrued is $" + accruedInt);
    }
    
    

    public static double interestQuarterly(double bal, 
                                            double interest, 
                                            int years) {
        //System.out.println(bal);
        //System.out.println(interest);
        double afterInterest = bal;
        double qInt = (interest/4)/100;
        double intEarned = 0.0;
        
        for (int i = 0; i < (years*4); i++) {
            afterInterest = afterInterest * (1 + qInt);
            intEarned = afterInterest - bal;
            if(i % 4 == 0 && i != 0){
                System.out.println("Your total interest earned is $" + intEarned); 
                System.out.println("after " + (i/4) + " year(s). The current balance is $" + afterInterest);
            }
        }
        return afterInterest;
    }
}
