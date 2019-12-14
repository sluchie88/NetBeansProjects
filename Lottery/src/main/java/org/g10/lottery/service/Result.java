package org.g10.lottery.service;

public class Result<T> extends Response {

    //working, don't touch. seems like a generic class used in Dao writer methods
    
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
