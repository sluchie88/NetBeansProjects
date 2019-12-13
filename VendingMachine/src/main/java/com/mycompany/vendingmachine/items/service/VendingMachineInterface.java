/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.items.service;

import com.mycompany.vendingmachine.dao.NoItemInventoryException;
import com.mycompany.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author TomTom
 */
public interface VendingMachineInterface {
    
    public Map<String, Item> getStock();
    
    public Item buyItem(String code) throws NoItemInventoryException, InsufficientFundsException;
    
    public int[] getChange();
    
    public BigDecimal addMoney(BigDecimal insertedMoney);
}
