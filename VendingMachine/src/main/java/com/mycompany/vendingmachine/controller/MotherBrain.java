/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.controller;

import com.mycompany.vendingmachine.dao.NoItemInventoryException;
import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.items.service.InsufficientFundsException;
import com.mycompany.vendingmachine.items.service.VendingMachine;
import com.mycompany.vendingmachine.view.VendingView;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author TomTom
 */
public class MotherBrain {

    private VendingMachine vm;
    private VendingView vv;
    private BigDecimal insertedAmt = BigDecimal.ZERO;

    public MotherBrain(VendingMachine bender, VendingView fry) {
        this.vm = bender;
        this.vv = fry;
    }

    public void run() {
        boolean runProgram = true;
        String menuSelection;
        viewCurrentStock();
        while (runProgram) {
            if (insertedAmt.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("\nCurrent amount inserted: $" + insertedAmt + "\n");
            }
            //vv.displayCurrentAmountInserted();
            menuSelection = vv.getMenuSelection();

            if (menuSelection.equals("0")) {
                runProgram = false;
            } else if (menuSelection.equals("1")) {
                viewCurrentStock();
            } else if (menuSelection.equals("2")) {
                insertMoney();
            } else if (menuSelection.equals("3") && insertedAmt.compareTo(BigDecimal.ZERO) > 0) {
                purchaseItem();
            } else if (menuSelection.equals("3") && insertedAmt.compareTo(BigDecimal.ZERO) == 0) {
                vv.displayInsertMoney();
            } else if (menuSelection.equals("4") && insertedAmt.compareTo(BigDecimal.ZERO) > 0) {
                dispenseChange();
            } else if (menuSelection.equals("4") && insertedAmt.compareTo(BigDecimal.ZERO) == 0) {
                vv.displayInsertMoney();
            }

        }
        vv.printGoodbyeMessage();

    }

    private void unknownCommand() {
        vv.displayErrorMessage();
    }

    private void viewCurrentStock() {
        Map<String, Item> machine = vm.getStock();
        vv.displayVendingMachine(machine);
    }

    private void insertMoney() {
        BigDecimal additionalMoneys = vv.getAmountInserted();
        insertedAmt = insertedAmt.add(additionalMoneys);
    }

    private void purchaseItem() {
        Map<String, Item> machine = vm.getStock();
        vv.displayVendingMachine(machine);
        Item buying = new Item();
        String uChoice = vv.getDesiredItemToPurchase();
        try {
            buying = vm.buyItem(uChoice, insertedAmt);
        } catch (InsufficientFundsException | NoItemInventoryException ie) {
            ie.getMessage();
        }
        insertedAmt = insertedAmt.subtract(buying.getPrice());
        vv.displaySuccess();
    }

    private void dispenseChange() {
        int[] changeArray = vm.getChange(insertedAmt);
        insertedAmt = BigDecimal.ZERO;
        vv.displayChange(changeArray);
    }
}
