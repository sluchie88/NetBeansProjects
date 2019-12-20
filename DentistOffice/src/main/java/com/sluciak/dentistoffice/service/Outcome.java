/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

/**
 *
 * @author TomTom
 */
public class Outcome<T> extends ErrorMessage {

    //working, don't touch. seems like a generic class used in Dao writer methods
    
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}

