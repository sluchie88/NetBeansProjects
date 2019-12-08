/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movies.dao;

/**
 *
 * @author TomTom
 */
public class MovieNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>MovieNotFoundException</code> without
     * detail message.
     */
    public MovieNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MovieNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MovieNotFoundException(String msg) {
        super(msg);
    }
}
