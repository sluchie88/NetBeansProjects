/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.view;

/**
 *
 * @author TomTom
 */
public enum MainMenuOptions {
    EXIT(0, "Exit"),
    DISPLAY_BY_DR_DATE(1, "Display appointments by Date and Doctor"),
    VIEW_APPT(2, "View appointment by patient"),
    SCHEDULE_APPT(3, "Schedule an appointment"),
    UPDATE_APPT(4, "Update an appointment"),
    CREATE_PATIENT(5, "Create a new patient"),
    CANCEL_APPT(6, "Cancel an appointment");
    
    private final int value;
    private final String name;
    
    private MainMenuOptions(int val, String name){
        this.value = val;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    
    public static MainMenuOptions fromValue(int value) {
        for (MainMenuOptions mmo : MainMenuOptions.values()) {
            if (mmo.getValue() == value) {
                return mmo;
            }
        }
        return EXIT;
    }
}
