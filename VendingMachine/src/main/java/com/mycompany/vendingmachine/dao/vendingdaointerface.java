/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.Item;
import java.util.Map;

/**
 *
 * @author TomTom
 */
public interface vendingdaointerface {
    
    public Item vendItem(String code) throws NoItemInventoryException;
    
    public Map<String, Item> getAllStock();
    
}
