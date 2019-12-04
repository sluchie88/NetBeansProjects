
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nicolasgraese
 */
public class TextAdventure {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        //Ground = 0, Upstairs = 1, Downstairs = -1, Outside = 2
        int location = 0;
        int inventory = 0;
        int MAX_INVENTORY = 4;
        boolean ring = false;
        boolean ball = false;
        boolean shifter = false;
        boolean key = false;
        boolean door = false;
        boolean soulSeeker = false;
        boolean endRoom = false;
        while(!endRoom)
        {
            int decision;
            if(location == 0)
            {
                System.out.println("You are on the ground level");
                if(key == false)
                {
                    System.out.println("You see an ornate key hanging on the wall.");
                }
                System.out.println("What would you like to do?");
                if(inventory != 0)
                {
                   System.out.println("0 - Drop an item or check inventory"); 
                }    
                System.out.println("1 - Go into the basement");
                System.out.println("2 - Go upstairs");
                System.out.println("3 - Go outside");
                System.out.println("4 - Fight the other mother");
                if(key == false)
                {
                   System.out.println("5 - Take the key off the wall.");         
                }
                else
                {
                   System.out.println("5 - Unlock the door"); 
                }
                
                decision = scanner.nextInt();
                scanner.nextLine();
                
                //drop an item branch
                if(decision == 0 && inventory != 0)
                {
                    String userInput;
                    System.out.println("Choose an item to drop: ");
                    if(ring == true)
                    {
                        System.out.println("ring");
                    }
                    if(key == true)
                    {
                        System.out.println("key");
                    }
                    if(ball == true)
                    {
                        System.out.println("ball");
                    }
                    if(shifter == true)
                    {
                        System.out.println("shifter");
                    }
                    if(soulSeeker == true)
                    {
                        System.out.println("soul seeker");
                    }
                    System.out.println("exit");
                    userInput = scanner.nextLine();
                    if(userInput.equals("ring") && ring == true)
                    {
                        System.out.println("You drop the ring. It disapears back to its original location");
                        ring = false;
                        inventory--;
                    }
                    else if(userInput.equals("key") && key == true)
                    {
                        System.out.println("You drop the key. It disapears back to its original location");
                        key = false;
                        inventory--;
                    }
                    else if(userInput.equals("ball") && ball == true)
                    {
                        System.out.println("You drop the ball. It disapears back to its original location");
                        ball = false;
                        inventory--;
                    }
                    else if(userInput.equals("shifter") && shifter == true)
                    {
                        System.out.println("You drop the shifter. It disapears back to its original location");
                        shifter = false;
                        inventory--;
                    }
                    else if(userInput.equals("soul seeker") && soulSeeker == true){
                        System.out.println("You drop the soul seeker. It disappears back to its original location");
                        inventory--;
                        soulSeeker = false;
                    }
                    else if(userInput.equals("exit")){
                        System.out.println("You admire your pretty items, but decide to get back to adventuring.");
                    }
                    else
                    {
                        System.out.println("Enter a valid object name.");
                    }
                }
                else if(decision == 1)
                {
                    location = -1;
                    System.out.println("You walk down to the basement");
                }
                else if(decision == 2)
                {
                    location = 1;
                    System.out.println("You walk up to the upstairs");
                }
                else if(decision == 3)
                {
                    location = 2;
                    System.out.println("You walk outside");
                }
                else if(decision == 4)
                {
                    if(door == true){
                        endRoom = true;
                        System.out.println("You go to fight the other mother");
                    }
                    else
                    {
                        System.out.println("You try to enter the door, but it doesn't budge.");
                        System.out.println("You notice an ornate keyhole.");
                    }
                }
                else if(decision == 5 && key == false && inventory != MAX_INVENTORY)
                {
                    key = true;
                    inventory++;
                    System.out.println("You pick up the key. It feels weighty in your hand.");
                }
                else if (decision == 5 && key == false && inventory == MAX_INVENTORY)
                {
                    System.out.println("You are over-encumbered. Please drop an item.");
                }
                else if (decision == 5 && key == true)
                {
                    door = true;
                    key = false;
                    inventory--;
                    System.out.println("A lound clang sounds as the gears turn.");
                    System.out.println("The door creaks open ominously.");
                }
                else
                {
                    System.out.println("Please input a valid number");
                }
            }
            

//Branch for upstairs
            else if(location == 1)
            {
                System.out.println("You are upstairs");
                System.out.println("You see a heavy safe sitting in the corner.");
                if(ring == false)
                {
                    System.out.println("You see a ring on the table.");
                }
                System.out.println("What would you like to do?");
                if(inventory != 0)
                {
                   System.out.println("0 - Drop an item or check inventory"); 
                }
                System.out.println("1 - Go to the ground level");
                System.out.println("2 - Inspect safe");
                if(ring == false)
                {
                    System.out.println("3 - Pick up the ring");
                }
                //System.out.println("3 - ");
                //System.out.println("4 - ");
                decision = scanner.nextInt();
                scanner.nextLine();
                
                //drop an item branch
                if(decision == 0 && inventory != 0)
                {
                    String userInput;
                    System.out.println("Choose an item to drop: ");
                    if(ring == true)
                    {
                        System.out.println("ring");
                    }
                    if(key == true)
                    {
                        System.out.println("key");
                    }
                    if(ball == true)
                    {
                        System.out.println("ball");
                    }
                    if(shifter == true)
                    {
                        System.out.println("shifter");
                    }
                    if(soulSeeker == true)
                    {
                        System.out.println("soul seeker");
                    }
                    System.out.println("exit");
                    userInput = scanner.nextLine();
                    if(userInput.equals("ring") && ring == true)
                    {
                        System.out.println("You drop the ring. It disapears back to its original location");
                        ring = false;
                        inventory--;
                    }
                    else if(userInput.equals("key") && key == true)
                    {
                        System.out.println("You drop the key. It disapears back to its original location");
                        key = false;
                        inventory--;
                    }
                    else if(userInput.equals("ball") && ball == true)
                    {
                        System.out.println("You drop the ball. It disapears back to its original location");
                        ball = false;
                        inventory--;
                    }
                    else if(userInput.equals("shifter") && shifter == true)
                    {
                        System.out.println("You drop the shifter. It disapears back to its original location");
                        shifter = false;
                        inventory--;
                    }
                    else if(userInput.equals("soul seeker") && soulSeeker == true){
                        System.out.println("You drop the soul seeker. It disappears back to its original location");
                        inventory--;
                        soulSeeker = false;
                    }
                    else if(userInput.equals("exit")){
                        System.out.println("You admire your pretty items, but decide to get back to adventuring.");
                    }
                    else
                    {
                        System.out.println("Enter a valid object name.");
                    }
                }
                
                
                //branch for making decisions what to do
                else if(decision == 1)
                {
                    location = 0;
                    System.out.println("You walk down to the ground level");
                }
                else if (decision == 2){
                    System.out.println("You see a dial on the safe.");
                    System.out.println("Input code: ");
                    String safeCode = "23 4 56";
                    String userSafeCode = scanner.nextLine();
                    if (userSafeCode.equals(safeCode)){
                        System.out.println("The safe door swings open. It seems well oiled.");
                        if(soulSeeker == true)
                        {
                            System.out.println("The safe is empty aside from a few cobwebs.");
                        }
                        else
                        {
                            System.out.println("You pick up the Soul Seeker.");
                            soulSeeker = true;
                            inventory++;
                        }
                    }
                    else{
                        System.out.println("The door refuses to budge.");
                        System.out.println("Maybe someone nearby knows the code...");
                    }
                }
                
                //for picking up the ring
                else if(decision == 3 && ring == false && inventory != MAX_INVENTORY)
                {
                    ring = true;
                    inventory++;
                    System.out.println("You pick up the ring");
                }
                else if (decision == 3 && ring == false && inventory == MAX_INVENTORY)
                {
                    System.out.println("You are over-encumbered. Please drop an item.");
                }
                else
                {
                    System.out.println("Please input a valid number");
                }
            }
            //basement branch
            else if(location == -1)
            {
                System.out.println("You are in the basement");
                if(ball == false)
                {
                    System.out.println("You see a red ball on the floor.");
                }
                System.out.println("What would you like to do?");
                if(inventory != 0)
                {
                   System.out.println("0 - Drop an item or check inventory"); 
                }
                System.out.println("1 - Go to the ground level");
                if(ball == false)
                {
                    System.out.println("2 - Pick up the ball");
                }
                //System.out.println("3 - ");
                //System.out.println("4 - ");
                decision = scanner.nextInt();
                scanner.nextLine();
                
//drop an item branch
                if(decision == 0 && inventory != 0)
                {
                    String userInput;
                    System.out.println("Choose an item to drop: ");
                    if(ring == true)
                    {
                        System.out.println("ring");
                    }
                    if(key == true)
                    {
                        System.out.println("key");
                    }
                    if(ball == true)
                    {
                        System.out.println("ball");
                    }
                    if(shifter == true)
                    {
                        System.out.println("shifter");
                    }
                    if(soulSeeker == true)
                    {
                        System.out.println("soul seeker");
                    }
                    System.out.println("exit");
                    userInput = scanner.nextLine();
                    if(userInput.equals("ring") && ring == true)
                    {
                        System.out.println("You drop the ring. It disapears back to its original location");
                        ring = false;
                        inventory--;
                    }
                    else if(userInput.equals("key") && key == true)
                    {
                        System.out.println("You drop the key. It disapears back to its original location");
                        key = false;
                        inventory--;
                    }
                    else if(userInput.equals("ball") && ball == true)
                    {
                        System.out.println("You drop the ball. It disapears back to its original location");
                        ball = false;
                        inventory--;
                    }
                    else if(userInput.equals("shifter") && shifter == true)
                    {
                        System.out.println("You drop the shifter. It disapears back to its original location");
                        shifter = false;
                        inventory--;
                    }
                    else if(userInput.equals("soul seeker") && soulSeeker == true){
                        System.out.println("You drop the soul seeker. It disappears back to its original location");
                        inventory--;
                        soulSeeker = false;
                    }
                    else if(userInput.equals("exit")){
                        System.out.println("You admire your pretty items, but decide to get back to adventuring.");
                    }
                    else
                    {
                        System.out.println("Enter a valid object name.");
                    }
                }                
                //making decisions for what to do branch
                else if(decision == 1)
                {
                    location = 0;
                    System.out.println("You walk up to the ground level");
                }
                else if(decision == 2 && ball == false && inventory != MAX_INVENTORY)
                {
                    ball = true;
                    inventory++;
                    System.out.println("You pick up the ball");
                }
                else if (decision == 2 && ball == false && inventory == MAX_INVENTORY)
                {
                    System.out.println("You are over-encumbered. Please drop an item.");
                }
                else
                {
                    System.out.println("Please input a valid number");
                }
            }
            
            //branch for outside
            else //if(location == 2)
            {
                System.out.println("You are outside");
                if(shifter == false)
                {
                    System.out.println("You see a shifter in the bird bath.");
                }
                System.out.println("What would you like to do?");
                if(inventory != 0)
                {
                   System.out.println("0 - Drop an item or check inventory"); 
                }
                System.out.println("1 - Go inside to the ground level");
                if(shifter == false)
                {
                    System.out.println("2 - Pick up the shifter");
                }
                //System.out.println("3 - ");
                //System.out.println("4 - ");
                decision = scanner.nextInt();
                scanner.nextLine();
                
               //drop an item branch
                if(decision == 0 && inventory != 0)
                {
                    String userInput;
                    System.out.println("Choose an item to drop: ");
                    if(ring == true)
                    {
                        System.out.println("ring");
                    }
                    if(key == true)
                    {
                        System.out.println("key");
                    }
                    if(ball == true)
                    {
                        System.out.println("ball");
                    }
                    if(shifter == true)
                    {
                        System.out.println("shifter");
                    }
                    if(soulSeeker == true)
                    {
                        System.out.println("soul seeker");
                    }
                    System.out.println("exit");
                    userInput = scanner.nextLine();
                    if(userInput.equals("ring") && ring == true)
                    {
                        System.out.println("You drop the ring. It disapears back to its original location");
                        ring = false;
                        inventory--;
                    }
                    else if(userInput.equals("key") && key == true)
                    {
                        System.out.println("You drop the key. It disapears back to its original location");
                        key = false;
                        inventory--;
                    }
                    else if(userInput.equals("ball") && ball == true)
                    {
                        System.out.println("You drop the ball. It disapears back to its original location");
                        ball = false;
                        inventory--;
                    }
                    else if(userInput.equals("shifter") && shifter == true)
                    {
                        System.out.println("You drop the shifter. It disapears back to its original location");
                        shifter = false;
                        inventory--;
                    }
                    else if(userInput.equals("soul seeker") && soulSeeker == true){
                        System.out.println("You drop the soul seeker. It disappears back to its original location");
                        inventory--;
                        soulSeeker = false;
                    }
                    else if(userInput.equals("exit")){
                        System.out.println("You admire your pretty items, but decide to get back to adventuring.");
                    }
                    else
                    {
                        System.out.println("Enter a valid object name.");
                    }
                }
                
                //branch for making decisions for what to do
                else if(decision == 1)
                {
                    location = 0;
                    System.out.println("You walk inside to the ground level");
                }
                else if(decision == 2 && shifter == false && inventory != MAX_INVENTORY)
                {
                    shifter = true;
                    inventory++;
                    System.out.println("You pick up the shifter");
                }
                else if (decision == 2 && shifter == false && inventory == MAX_INVENTORY)
                {
                    System.out.println("You are over-encumbered. Please drop an item.");
                }
                else
                {
                    System.out.println("Please input a valid number");
                }
            }
        }
        //outside the while loop
        //end game branch
        System.out.println("You're fighting the other mother!!!");
        System.out.println("You need all the items to win!");
        if(ring == true && ball == true && shifter == true)
        {
            System.out.println("You defeat the other mother!!");
        }
        else
        {
            System.out.println("She takes your eyes, yikes.");
        }
        System.out.println("Game Over");
    }
}
