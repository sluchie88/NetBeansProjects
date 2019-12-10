/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.view;

import com.mycompany.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author TomTom
 */
public class VendingView {

    UserIO uio = new UserIO();

    public String getMenuSelection() {
        uio.printLine("[0] Exit Program");
        uio.printLine("1. View item stock");
        uio.printLine("2. Add money");
        uio.printLine("3. Purchase an item");
        uio.printLine("4. Dispense change");
        return uio.readString("Please enter a number between [0-4]");
    }

    public void displayCurrentAmountInserted() {

    }

    public void displayErrorMessage() {
        uio.printLine("=== ERROR ===");
    }

    public void printGoodbyeMessage() {
        uio.printLine("\nThank you, come again!\n");
    }

    public void displayVendingMachine(Map<String, Item> machine) {
        int counter = 0;
        for (Map.Entry<String, Item> i : machine.entrySet()) {
            uio.print("Code: " + i.getValue().getCode() + ". ");
            uio.print(i.getValue().getName() + ". ");
            uio.print("Costs: $" + i.getValue().getPrice());
            uio.print(". Number available: " + i.getValue().getStock());
            if (counter % 4 == 0) {
                uio.print("\n");
            }
        }
    }

    public BigDecimal getAmountInserted() {
        BigDecimal insertedAmt = uio.readBigDecimal("Please enter the amount you would like to insert: [1-10]");
        uio.readString("");
        return insertedAmt;
    }

    public void displayChange(int[] changeArray) {
        uio.printLine("Your change comes to: ");
        uio.printLine(changeArray[0] + " quarter(s).");
        uio.printLine(changeArray[1] + " dime(s).");
        uio.printLine(changeArray[2] + " nickel(s).");
        uio.printLine("and " + changeArray[3] + " pennies.\n");
        uio.readString("Please press enter to continue.");
    }

    public String getDesiredItemToPurchase() {
        return uio.readString("Enter the code for the desired item: ");

    }

    public void displaySuccess() {
        uio.printLine("+++ Success! +++\n");
    }

    public void displayInsertMoney() {
        uio.printLine("\n--- You must insert money before you can do that ---\n");
    }

}
