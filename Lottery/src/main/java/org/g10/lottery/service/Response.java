package org.g10.lottery.service;

import java.util.ArrayList;
import java.util.List;

public class Response {

    /*
    Seems to be a class for holding error messages, rather than using
    Exceptions. Holds all errorMessages in an arraylist
    */
    
    private final ArrayList<String> errorMessages = new ArrayList<>();

    public boolean hasError() {
        return errorMessages.size() > 0;
    }

    public void addError(String message) {
        errorMessages.add(message);
    }

    public List<String> getErrors() {
        return new ArrayList<>(errorMessages);
    }
}
