/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movies.dao;

import com.mycompany.movies.dto.Movie;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface Dao {

    //adds given movie to the list, then writes it to the file
    //checks for same movie title. If exists, checks for release year, 
    //then director to ensure it's a double entry
    Movie addMovie(Movie movie) throws DaoException;

    List<Movie> getAllMovies() throws DaoException;

    List<Movie> getMovie(String title) throws DaoException;

    List<Movie> searchByKeyWord(String search) throws DaoException;

    Movie findByID(int id) throws DaoException;

    boolean removie(int ID) throws DaoException;

    Movie editMovie(Movie movie) throws DaoException;
}
