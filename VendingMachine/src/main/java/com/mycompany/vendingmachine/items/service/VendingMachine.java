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

    public VendingMachine(VendingDao dao, VendingView view) {
        this.dd = dao;
        this.vv = view;
    }

    @Override
    public Map<String, Item> getStock() {
        return dd.getAllStock();
    }

    @Override
    public Item buyItem(String code, BigDecimal wallet) throws NoItemInventoryException, InsufficientFundsException {
        Item purchasing = dd.vendItem(code);
        if (purchasing != null) {
            if (wallet.compareTo(purchasing.getPrice()) >= 0) {
                return purchasing;
            } else {
                throw new InsufficientFundsException("Insufficient funds.");
            }

        } else {
            throw new NoItemInventoryException("Item out of stock.");
        }
    }

    @Override
    public String giveChange() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int[] getChange(BigDecimal amtToChange) {
        int numQrtrs = 0;
        int numDimes = 0;
        int numNickels = 0;
        int numPennies = 0;
        BigDecimal q = new BigDecimal("0.25");
        BigDecimal d = new BigDecimal("0.10");
        BigDecimal n = new BigDecimal("0.05");
        BigDecimal p = new BigDecimal("0.01");

        while (amtToChange.compareTo(BigDecimal.ZERO) != 0) {
            if (amtToChange.subtract(q).compareTo(BigDecimal.ZERO) >= 0) {
                numQrtrs++;
                amtToChange = amtToChange.subtract(q);
            } else if (amtToChange.subtract(d).compareTo(BigDecimal.ZERO) >= 0) {
                numDimes++;
                amtToChange = amtToChange.subtract(d);
            } else if (amtToChange.subtract(n).compareTo(BigDecimal.ZERO) >= 0) {
                numNickels++;
                amtToChange = amtToChange.subtract(n);
            } else {
                numPennies++;
                amtToChange = amtToChange.subtract(p);
            }
        }
        int[] changeTotals = {numQrtrs, numDimes, numNickels, numPennies};
        return changeTotals;
    }

}
