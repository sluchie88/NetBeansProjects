/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class ErrorMessage {

    private final ArrayList<String> errorMessages = new ArrayList<>();

    public void addErrors(String message) {
        errorMessages.add(message);
    }

    public boolean hasError() {
        return errorMessages.size() > 0;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errorMessages);
    }

    public void deleteErrors(ErrorMessage em) {
        if (em.hasError()) {
            em.errorMessages.clear();
        }
    }
}
