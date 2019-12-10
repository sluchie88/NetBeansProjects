/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author TomTom
 */
public class VendingDao implements vendingdaointerface {

    public static final String FILE_PATH = "stock.txt";
    public static final String DELIMITER = "::";
    private Map<String, Item> inventory = new HashMap<>();

    @Override
    /*
    vends item passed in. decrements its stock, if available (throws NoItemInventory if none)
    saves to file after vending
     */
    public Item vendItem(String code) throws NoItemInventoryException {

        Item item = inventory.get(code);

        if (item != null && item.getStock() != 0) {
            Item sameButNew = item;
            sameButNew.setStock(item.getStock() - 1);
            inventory.put(sameButNew.getCode(), sameButNew);
            try {
                saveToFile();
                return item;
            } catch (IOException ex) {
            }
        }

        return null;
    }

    @Override
    public Map<String, Item> getAllStock() {
        return inventory;
    }

    /*
    takes in each line of the text file and breaks it into an array, placing
    each token in a spot. Then takes the info from the array and sets the item's properties
    accordingly
     */
    private Item unmarshallItem(String itemStockList) {
        String[] itemTokens = itemStockList.split(DELIMITER);
        Item itemIn = new Item();

        itemIn.setCode(itemTokens[0]);
        itemIn.setName(itemTokens[1]);

        BigDecimal price = new BigDecimal(itemTokens[2]);
        itemIn.setPrice(price);

        itemIn.setStock(Integer.parseInt(itemTokens[3]));

        return itemIn;
    }

    //turns a item and its field into a string separated by the delimiter
    private String marshallItem(Item anItem) {
        String itemAdded = "";
        itemAdded += anItem.getCode() + DELIMITER;
        itemAdded += anItem.getName() + DELIMITER;
        itemAdded += anItem.getPrice() + DELIMITER;
        itemAdded += anItem.getStock();
        return itemAdded;
    }

    //just writes the items in the linked list to the file
    private void saveToFile() throws IOException {
        try (PrintWriter lukeFilewalker = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Item> i : inventory.entrySet()) {
                lukeFilewalker.println(marshallItem(i.getValue()));
            }
        }
    }

    public void loadFile() throws FileNotFoundException {
        Scanner scanny;
        try {
            scanny = new Scanner(new BufferedReader(new FileReader(FILE_PATH)));
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        }

        String currLine;
        Item currItem;

        while (scanny.hasNextLine()) {
            currLine = scanny.nextLine();
            currItem = unmarshallItem(currLine);
            inventory.put(currItem.getCode(), currItem);
        }
        scanny.close();
    }

}
