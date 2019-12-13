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

    public MotherBrain(VendingMachine bender, VendingView fry) {
        this.vm = bender;
        this.vv = fry;
    }

    public void run() {
        boolean runProgram = true;
        boolean displayAdminMenu = false;
        String menuSelection;
        viewCurrentStock();
        while (runProgram) {
            BigDecimal insertedAmt = getCurrentAmountInserted();
            menuSelection = vv.getMenuSelection();

            if (menuSelection.equals("0") && insertedAmt.compareTo(BigDecimal.ZERO) > 0) {
                dispenseChange();
                runProgram = false;
            }else if(menuSelection.equals("0")){
                runProgram = false;
            }else if (menuSelection.equals("1")) {
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
            } else if(menuSelection.equals("01234")){
                displayAdminMenu = true;
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
    
    private BigDecimal getCurrentAmountInserted(){
        vv.displayCurrentAmountInserted(vm.retrieveInsertedMoneys());
        return vm.retrieveInsertedMoneys();
    }

    //allows the user to insert money. works with view to gather the amount the user
    //wishes to insert
    private void insertMoney() {
        BigDecimal additionalMoneys = vv.getAmountInserted();
        vm.addMoney(additionalMoneys);
        vv.displaySuccess();
    }

    /*
    responsible for passing the code of the item the user wants to buy down to the service
    Catches either the InsufficientFunds or NoItemInventory exceptions and displays their message
    */
    private void purchaseItem() {
        Map<String, Item> machine = vm.getStock();
        vv.displayVendingMachine(machine);
        Item buying = new Item();
        String uChoice = vv.getDesiredItemToPurchase();
        try {
            buying = vm.buyItem(uChoice);
            //insertedAmt = insertedAmt.subtract(buying.getPrice());
        vv.displaySuccess();
        } catch (InsufficientFundsException | NoItemInventoryException ie) {
            vv.displayExceptionMessage(ie.getMessage());
        }
        
    }

    /*
    calls to the method in the service responsible for calculating the change due.
    hands to the resulting int array off to the view for displaying
    */
    private void dispenseChange() {
        int[] changeArray = vm.getChange();
        vv.displayChange(changeArray);
    }
}
