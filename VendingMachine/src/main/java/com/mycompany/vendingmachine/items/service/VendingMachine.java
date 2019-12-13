/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.items.service;

import com.mycompany.vendingmachine.dao.NoItemInventoryException;
import com.mycompany.vendingmachine.dao.VendingDao;
import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.view.VendingView;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author TomTom
 */
public class VendingMachine implements VendingMachineInterface {

    private VendingDao dd;
    private VendingView vv;
    private BigDecimal insertedAmount = BigDecimal.ZERO;

    public VendingMachine(VendingDao dao, VendingView view) {
        this.dd = dao;
        this.vv = view;
    }

    @Override
    public Map<String, Item> getStock() {
        return dd.getAllStock();
    }

    @Override
    /*
    takes in the code for the item the user wants to buy along with the amount inserted into the machine
    If the dao throws an NoItemInventory exception, passes it up to the service.
    Checks the price of the item, if not enough has been inserted, throws an Insufficient funds Exception
     */
    public Item buyItem(String code) throws InsufficientFundsException, NoItemInventoryException {
        Item purchasing = new Item();

        purchasing = dd.vendItem(code, insertedAmount);

        if (purchasing.getStock() >= 1) {
            if (insertedAmount.compareTo(purchasing.getPrice()) >= 0) {
                insertedAmount = insertedAmount.subtract(purchasing.getPrice());
                return purchasing;
            } else {
                throw new InsufficientFundsException("Insufficient funds. Please insert more money.");
            }

        }
        return purchasing;
    }

    @Override
    /*
    dispenses change to the user. Goes down the list, starting with the biggest (quarter)
    attempts to subtract a particular coin, if result is still greater than 0, augments 
    total number of coins of that type. Stores and returns results in an int array
     */
    public int[] getChange() {
        int numQrtrs = 0;
        int numDimes = 0;
        int numNickels = 0;
        int numPennies = 0;
        BigDecimal q = new BigDecimal("0.25");
        BigDecimal d = new BigDecimal("0.10");
        BigDecimal n = new BigDecimal("0.05");
        BigDecimal p = new BigDecimal("0.01");

        while (insertedAmount.compareTo(BigDecimal.ZERO) > 0) {
            if (insertedAmount.subtract(q).compareTo(BigDecimal.ZERO) >= 0) {
                numQrtrs++;
                insertedAmount = insertedAmount.subtract(q);
            } else if (insertedAmount.subtract(d).compareTo(BigDecimal.ZERO) >= 0) {
                numDimes++;
                insertedAmount = insertedAmount.subtract(d);
            } else if (insertedAmount.subtract(n).compareTo(BigDecimal.ZERO) >= 0) {
                numNickels++;
                insertedAmount = insertedAmount.subtract(n);
            } else {
                numPennies++;
                insertedAmount = insertedAmount.subtract(p);
            }
        }
        int[] changeTotals = {numQrtrs, numDimes, numNickels, numPennies};
        return changeTotals;
    }

    @Override
    public BigDecimal addMoney(BigDecimal insertedMoney) {
        insertedAmount = insertedAmount.add(insertedMoney);
        return insertedAmount;
    }

    public BigDecimal retrieveInsertedMoneys() {
        return insertedAmount;
    }
}
