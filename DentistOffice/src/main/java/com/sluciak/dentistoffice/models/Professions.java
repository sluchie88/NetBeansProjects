/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.models;

/**
 *
 * @author TomTom
 */
public enum Professions {
    DENTIST(0, "DENTIST"),
    ORTHODONTIST(1, "ORTHODONTIST"),
    HYGIENIST(2, "HYGIENIST"),
    ORAL_SURGEON(3, "ORAL_SURGEON");

    private final int value;
    private final String jobTitle;

    private Professions(int value, String type) {
        this.value = value;
        this.jobTitle = type;
    }

    public int getValue() {
        return value;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public static Professions fromValue(int value) {
        for (Professions pro : Professions.values()) {
            if (pro.getValue() == value) {
                return pro;
            }
        }
        return DENTIST;
    }

    public static Professions fromString(String str) {
        
        if (DENTIST.getJobTitle().equalsIgnoreCase(str)) {
            return DENTIST;
        } else if(ORTHODONTIST.getJobTitle().equalsIgnoreCase(str)){
            return ORTHODONTIST;
        }
        else if(HYGIENIST.getJobTitle().equalsIgnoreCase(str)){
            return HYGIENIST;
        }else{
            return ORAL_SURGEON;
        }
    }
}
