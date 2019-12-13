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
import java.util.Arrays;
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
public class VendingMachineIT {

    public VendingMachineIT() {
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
     * Test of getStock method, of class VendingMachine.
     */
    @Test
    public void testGetStockPass() {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);

        assertFalse(greenMachine.getStock().isEmpty());
    }

//    @Test
//    public void testGetStockFail() {
//        VendingDao vDao = new VendingDao();
//        VendingView vView = new VendingView();
//        VendingMachine greenMachine = new VendingMachine(vDao, vView);
//    }
    /**
     * Test of buyItem method, of class VendingMachine.
     */
    @Test
    public void testBuyItemPass() throws Exception {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);
        Item purchased = new Item();
        BigDecimal moneys = BigDecimal.TEN;
        greenMachine.addMoney(moneys);

        try {
            greenMachine.buyItem("A1");
        } catch (InsufficientFundsException | NoItemInventoryException ie) {
            System.out.println(ie.getMessage());
        }
        assertNotNull(purchased);
    }

    @Test
    public void testBuyItemFail() throws Exception {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);
        greenMachine.addMoney(new BigDecimal("10.00"));

        try {
            Item purchased = greenMachine.buyItem("Z10");
            fail("Expected NoItemInventory Exception");
        } catch (NoItemInventoryException niie) {

        }
    }

    /**
     * Test of getChange method, of class VendingMachine.
     */
    @Test
    public void testGetChangePass() {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);
        greenMachine.addMoney(new BigDecimal("0.68"));

        int[] expResult = {2, 1, 1, 3};
        int[] result = greenMachine.getChange();
        assertTrue(Arrays.equals(result, expResult));
    }

    @Test
    public void testGetChangeFail() {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);
        greenMachine.addMoney(new BigDecimal("1.00"));

        int[] expResult = {0, 10, 0, 0};
        int[] result = greenMachine.getChange();

        assertFalse(Arrays.equals(result, expResult));
    }

    /**
     * Test of addMoney method, of class VendingMachine.
     */
    @Test
    public void testAddMoneyPass() {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);

        assertTrue(greenMachine.addMoney(new BigDecimal("2.25")).
                equals(new BigDecimal("2.25")));
    }

    @Test
    public void testAddMoneyFail() {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);

        assertFalse(greenMachine.addMoney(BigDecimal.ONE).equals(BigDecimal.TEN));
    }

    /**
     * Test of retrieveInsertedMoneys method, of class VendingMachine.
     */
    @Test
    public void testRetrieveInsertedMoneysPass() {
        VendingDao vDao = new VendingDao();
        VendingView vView = new VendingView();
        VendingMachine greenMachine = new VendingMachine(vDao, vView);
        greenMachine.addMoney(BigDecimal.ONE);
        assertNotNull(greenMachine.retrieveInsertedMoneys());
    }

}
