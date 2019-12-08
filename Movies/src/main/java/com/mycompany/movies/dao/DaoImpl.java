/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movies.dao;

import com.mycompany.movies.dto.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TomTom
 */
public class DaoImpl implements Dao {

    public static final String FILE_PATH = "movies.txt";
    public static final String DELIMITER = "::";
    private List<Movie> movieDB = new LinkedList<>();

    @Override
    /*
    First checks if the same movie already exists. Checks not just the title,
    but all other information as well, aside from user content. If everything doesn't
    match, then it will be assigned an ID and added to the Linked List
     */
    public Movie addMovie(Movie movie) {

        int movieID = 0;
        for (Movie m : movieDB) {
            movieID = Math.max(movieID, m.getID());
        }
        movie.setID(movieID + 1);
        movieDB.add(movie);
        try {
            saveToFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return movie;
    }

    @Override
    /*
    does the actual loading in from the text file and puts everything into the
    movieDB linked list. Was going to make a load method, but that would have just
    called to this method so I made a call to it in Main to actually load in the movies
     */
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Movie addMovie = unmarshallMovie(line);
                if (addMovie != null) {
                    allMovies.add(addMovie);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        movieDB = allMovies;
        return allMovies;
    }

    /*
    Originally had this return the first movie in the list that had the title the user
    was searching for. Changed it so it returns a list of movies with that title,
    since Hollywood loves remakes but not creative titles, thus making this project a breeze
     */
    @Override
    public List<Movie> getMovie(String title) {
        List<Movie> currMovie = new LinkedList<>();
        for (int i = 0; i < movieDB.size(); i++) {
            String currTitle = movieDB.get(i).getTitle();
            if (currTitle.equals(title)) {
                currMovie.add(movieDB.get(i));
            }
        }
        return currMovie;
    }

    /*
    a simple method that seraches through the DB for an ID and returns the movie attached to it
     */
    @Override
    public Movie findByID(int id) {
        Movie retMovie = new Movie();
        for (int i = 0; i < movieDB.size(); i++) {
            retMovie = movieDB.get(i);
            if (id == retMovie.getID()) {
                return retMovie;
            }
        }
        return null;
    }

    /*
    Changed the method so it converts the search term and movie title to lower case.
    Originally it was case sensitive, so if the user searched for 'jo', it would miss
    Joan of Arc (Ark?, too lazy to exercise my Google-fu right now). 
     */
    @Override
    public List<Movie> searchByKeyWord(String search) {
        List<Movie> found = new LinkedList<>();
        search = search.toLowerCase();
        for (int i = 0; i < movieDB.size(); i++) {
            if (movieDB.get(i).getTitle().toLowerCase().contains(search)) {
                Movie curr = movieDB.get(i);
                found.add(curr);
            }
        }
        return found;
    }

    /*
    If nothing else I like being creative with variable and method names. I make myself laugh, at least.
    This method sifts through the DB, until it finds the right movie.
    I made a separate method for removing a movie by its ID. It seemed more elegant than trying to do it
    by title, since there are so many movies that share a title but were released at different times.
    Ideally I would be able to return a list of movies with a matching title, then allow the user to select
    which they want to remove from that list. I'd like to try and implement that, but I'm not even sure
    where to start
     */
    @Override
    public boolean removie(int ID) {
        boolean successfullyRemoved = false;
        for (int i = 0; i < movieDB.size(); i++) {
            if (movieDB.get(i).getID() == ID) {
                try {
                    movieDB.remove(i);
                    saveToFile();
                    successfullyRemoved = true;
                } catch (IOException ex) {
                    Logger.getLogger(DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return successfullyRemoved;
    }

    @Override
    //resolve index within method, don't send back to controller
    public Movie editMovie(Movie movie) {
        int counter = getIndex(movie.getID());
        movieDB.set(counter, movie);
        try {
            saveToFile();
        } catch (IOException ex) {
            Logger.getLogger(DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movie;
    }

    private int getIndex(int id) {
        int counter = 0;
        while (counter < movieDB.size()) {
            if (movieDB.get(counter).getID() == id) {
                return counter;
            }
            counter++;
        }
        return counter;
    }

    /*
    takes in each line of the text file and breaks it into an array, placing
    each token in a spot. Then takes the info from the array and sets the movie's properties
    accordingly
     */
    private Movie unmarshallMovie(String movieFromText) {
        String[] movieTokens = movieFromText.split(DELIMITER);
        Movie movieIn = new Movie();

        int movieID = Integer.parseInt(movieTokens[0]);
        movieIn.setID(movieID);
        movieIn.setTitle(movieTokens[1]);
        movieIn.setReleaseDate(movieTokens[2]);
        movieIn.setRating(movieTokens[3]);
        movieIn.setDirector(movieTokens[4]);
        movieIn.setStudio(movieTokens[5]);
        movieIn.setUserRating(movieTokens[6]);

        return movieIn;
    }

    //turns a movie and its field into a string separated b
    private String marshallMovie(Movie aMovie) {
        String talkie = Integer.toString(aMovie.getID()) + DELIMITER;
        talkie += aMovie.getTitle() + DELIMITER;
        talkie += aMovie.getReleaseDate() + DELIMITER;
        talkie += aMovie.getRating() + DELIMITER;
        talkie += aMovie.getDirector() + DELIMITER;
        talkie += aMovie.getStudio() + DELIMITER;
        talkie += aMovie.getUserRating();
        return talkie;
    }

    //just writes the movies in the linked list to the file
    private void saveToFile() throws IOException {
        try (PrintWriter lukeFilewalker = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Movie m : movieDB) {
                lukeFilewalker.println(marshallMovie(m));
            }
        }
    }
}
