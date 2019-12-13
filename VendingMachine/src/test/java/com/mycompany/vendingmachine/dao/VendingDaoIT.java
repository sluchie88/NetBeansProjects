/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.items.service.InsufficientFundsException;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author TomTom
 */
public class VendingDaoIT {

    public VendingDaoIT() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of vendItem method, of class VendingDao.
     */
    @Test
    public void testVendItemPass() throws Exception {
        Item snack = new Item();
        snack.setCode("A4");
        snack.setName("Whatchamacallit");
        snack.setPrice(new BigDecimal("0.75"));
        snack.setStock(3);

        System.out.println("vendItem");
        String code = "A4";
        BigDecimal wallet = new BigDecimal("1.25");
        VendingDao vDao = new VendingDao();
        Item result = vDao.vendItem(code, wallet);
        assertNotNull(result);
    }

    @Test
    public void testVendNotEnoughCash() throws Exception, InsufficientFundsException {
        VendingDao vDao = new VendingDao();
        BigDecimal wallet = new BigDecimal("0.50");
        Item result2 = vDao.vendItem("C2", wallet);
        fail("Expected InsufficientFundsException for not enough cash");

    }

    @Test
    public void testVendNoInventory() throws Exception, NoItemInventoryException{
        VendingDao vDao = new VendingDao();
        BigDecimal coinPurse = new BigDecimal("5.00");
        Item wantedItem = vDao.vendItem("Z10", coinPurse);
        fail("Expected NoItemInventoryException for non-existent item.");
    }
    
    @Test
    public void testGetAllStockSuccess(){
        VendingDao vDao = new VendingDao();
        assertFalse(vDao.getAllStock().isEmpty());
    }

}
