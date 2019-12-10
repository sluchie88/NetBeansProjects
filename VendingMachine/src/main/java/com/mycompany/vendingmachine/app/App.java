/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.app;

import com.mycompany.vendingmachine.controller.MotherBrain;
import com.mycompany.vendingmachine.dao.VendingDao;
import com.mycompany.vendingmachine.items.service.VendingMachine;
import com.mycompany.vendingmachine.view.UserIO;
import com.mycompany.vendingmachine.view.VendingView;
import java.io.FileNotFoundException;

/**
 *
 * @author TomTom
 */
public class App {

    public static void main(String[] args) throws FileNotFoundException {
        UserIO io = new UserIO();
        VendingView viewer = new VendingView();
        VendingDao vDao = new VendingDao();
        vDao.loadFile();
        VendingMachine vm = new VendingMachine(vDao, viewer);
        MotherBrain controller = new MotherBrain(vm, viewer);
        controller.run();
    }
}
