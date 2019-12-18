/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

/**
 *
 * @author TomTom
 */
public class StorageException extends Exception {

    public StorageException(String msg) {
        super(msg);
    }
    
    public StorageException(String msg, Throwable innerEx){
        super(msg, innerEx);
    }
}
