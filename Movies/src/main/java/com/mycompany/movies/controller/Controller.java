/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movies.controller;

import com.mycompany.movies.dao.Dao;
import com.mycompany.movies.dao.DaoException;
import com.mycompany.movies.dto.Movie;
import com.mycompany.movies.ui.MovieListView;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class Controller {

    Dao dao;
    MovieListView v;

    public Controller(Dao dd, MovieListView view) {
        this.dao = dd;
        this.v = view;
    }

    public void run() {
        boolean runProg = true;
        String userSelection;
        try {
            while (runProg) {
                userSelection = getUserSelection();

                switch (userSelection) {
                    case "0":
                        runProg = false;
                        break;
                    case "1":
                        viewAll();
                        break;
                    case "2":
                        addMovie();
                        break;
                    case "3":
                        removie();
                        break;
                    case "4":
                        findMovieByTitle();
                        break;
                    case "5":
                        keywordSearch();
                        break;
                    case "6":
                        findByID();
                        break;
                    case "7":
                        updateMovie();
                        break;
                    default:
                        unknownCommand();
                }
            }
        } catch (DaoException e) {
            v.printErrorMessage(e.getMessage());
        }
        exitMessage();
    }

    //calls to View to print out the menu and grab user's selection
    private String getUserSelection() {
        return v.printMenuGetSelection();
    }

    //calls to View and prints out an error message that the user's input is invalid
    private void unknownCommand() {
        v.displayUnknownCommandMessage();
    }

    //calls to View to print out a goodbye message to users
    private void exitMessage() {
        v.displayGoodbye();
    }

    /*
    First checks if the same movie already exists. Checks not just the title,
    but all other information as well, aside from user comments. If everything doesn't
    match, then it will be passed into the DAO to get an ID and actually added to the DB
     */
    private void addMovie() throws DaoException {
        Movie newMovie = v.getMovieInfo();
        if (dao.addMovie(newMovie) != null) {
            dao.addMovie(newMovie);
            v.success();
        } else {
            v.duplicateMovie();
        }
    }

    //calls to view and prints all movies in the database
    private void viewAll() throws DaoException {
        List<Movie> allMovies = dao.getAllMovies();
        v.viewAll(allMovies);
    }

    //calls to view to get title searching for,
    //dao loops through linked list until movies with specified title are found
    //returns linked list, then calls to view to display the info of all found movies
    private void findMovieByTitle() throws DaoException {
        String title = v.getMovieTitle();
        List<Movie> movies = dao.getMovie(title);
        if (movies != null) {
            for (int i = 0; i < movies.size(); i++) {
                v.displayMovie(movies.get(i));
            }
            v.displayResultsMessage();
        } else {
            v.movieNotFound();
        }
    }

    //calls to view to get user's keyword
    //dao loops through entire linked list, stores any with keyword
    //in an new linked list, sends back here, then each movie is send to view to get displayed
    private void keywordSearch() throws DaoException {
        String kWord = v.getKeyword();
        List<Movie> results = dao.searchByKeyWord(kWord);
        if (!results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                v.displayMovie(results.get(i));
            }
            v.displayResultsMessage();
        } else {
            v.movieNotFound();
        }
    }

    /*
    Created a method to search for a movie by its ID rather than just a title or keyword
    I thought this could potentially be useful since some movies have the same title,
    and my search by title method simply returns the first movie found with that title
     */
    private void findByID() throws DaoException {
        int id = v.getMovieID();
        Movie movingPictures = dao.findByID(id);
        if (movingPictures != null) {
            v.displayMovie(movingPictures);
            v.displayResultsMessage();
        } else {
            v.movieNotFound();
        }
    }

    //need to grab movie and its index, edit the info, then put it back in place
    /*
    check that movie exists, pass the info to View and show info as user is editing
    don't send index back here, should only show up in Dao
     */
    private void updateMovie() throws DaoException {
        String title = v.getMovieTitle();
        List<Movie> matchingMovies = dao.getMovie(title);
        if (matchingMovies.size() > 0) {
            Movie updatedMovie;
            if (matchingMovies.size() == 1) {
                updatedMovie = v.updateMovieInfo(matchingMovies.get(0));
                dao.editMovie(updatedMovie);
                v.success();
            } else {
                for (int i = 0; i < matchingMovies.size(); i++) {
                    v.displayMovie(matchingMovies.get(i));
                }
                int idToEdit = v.getMovieIDToEdit();
                for (int j = 0; j < matchingMovies.size(); j++) {
                    updatedMovie = matchingMovies.get(j);
                    if (idToEdit == updatedMovie.getID()) {
                        updatedMovie = v.updateMovieInfo(updatedMovie);
                        dao.editMovie(updatedMovie);
                        updatedMovie.setID(idToEdit);
                        v.success();
                        break;
                    }
                }
            }

        } else {
            v.movieNotFound();
        }
    }

    //removie (haha, word play!) needs to search for a movie's title, 
    //then remove it from the list all without breaking the chain and 
    //losing what is linked to it. Dao removie method returns a boolean
    private void removie() throws DaoException {
        String title = v.getMovieTitle();
        List<Movie> matchingMovies = dao.getMovie(title);
        if (matchingMovies.size() <= 1) {
            if (matchingMovies.isEmpty()) {
                v.movieNotFound();
            } else {
                if (dao.removie(matchingMovies.get(0).getID())) {
                    v.success();
                } else {
                    v.movieNotFound();
                }
            }
        } else {
            for (Movie mv : matchingMovies) {
                v.displayMovie(mv);
            }
            int removingID = v.getMovieIDToRemove();
            for (Movie m : matchingMovies) {
                if (m.getID() == removingID) {
                    if (dao.removie(removingID)) {
                        v.success();
                    } else {
                        v.movieNotFound();
                    }
                }
            }
        }
    }

}
