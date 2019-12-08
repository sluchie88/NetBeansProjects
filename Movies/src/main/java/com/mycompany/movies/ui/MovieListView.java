/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movies.ui;

import com.mycompany.movies.dto.Movie;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class MovieListView {

    private UserIO aiyo;

    public MovieListView(UserIO io) {
        this.aiyo = io;
    }

    //prints out menu for user to select from, then gets their selection
    //apparently I initially typed this as "printMenGetSelection", which is
    //unimportant but I thought it was funny
    public String printMenuGetSelection() {
        aiyo.print("Main Menu");
        aiyo.print("1. View all movies in database");
        aiyo.print("2. Add a movie to the database");
        aiyo.print("3. Remove a movie from the database");
        aiyo.print("4. Search for movie(s) by title");
        aiyo.print("5. Search for movies with a keyword");
        aiyo.print("6. Search for a movie by ID");
        aiyo.print("7. Update a movie's information");
        aiyo.print("0. Exit program");

        return aiyo.readString("Please select from the above choices [0-6]");
    }

    //prints out prompts for new movie to be added to database
    //grabs user input and puts it into a new movie, then returns it
    public Movie getMovieInfo() {
        //user input section
        String title = aiyo.readString("Enter the movie's title");
        String releaseDate = aiyo.readString("Enter the movie's release date (if you don't know the exact date, enter the month and year)");
        String rating = aiyo.readString("Enter the movie's MPAA Rating [G, PG, PG-13, R, NC-17]");
        String director = aiyo.readString("Enter the director's name");
        String studio = aiyo.readString(("Enter the studio that made " + title));
        String userRating = aiyo.readString("Enter your breif comments on what you thought of the movie");

        //creates a new movie, then adds user input in before returning
        Movie currMovie = new Movie();
        currMovie.setTitle(title);
        currMovie.setReleaseDate(releaseDate);
        currMovie.setRating(rating);
        currMovie.setDirector(director);
        currMovie.setStudio(studio);
        currMovie.setUserRating(userRating);
        return currMovie;
    }

    public String getMovieTitle() {
        String title = aiyo.readString("Enter the movie's title");
        return title;
    }
    
    public int getMovieID(){
        int id = aiyo.readInt("Enter the ID of the movie you want ");
        return id;
    }
    
    public int getMovieIDToEdit(){
        int id = aiyo.readInt("Enter the ID of the movie you wish to edit: ");
        return id;
    }
    
    public int getMovieIDToRemove(){
        int id = aiyo.readInt("Enter the ID of the movie you want to remove:");
        return id;
    }

    public void displayUnknownCommandMessage() {
        aiyo.print("+++ Unknown Command. Enter a number between [0-6] +++");
    }

    public void displayGoodbye() {
        aiyo.print("+++ GOODBYE!! +++");
    }

    public void success() {
        aiyo.print("\n+++ Operation succssful +++\n\n");
    }

    public void movieNotFound() {
        aiyo.print("\n--- No results were found ---");
    }
    
    public void duplicateMovie(){
        aiyo.print("\n--- Movie already exists in database ---\n");
    }

    public void viewAll(List<Movie> movieDB) {
        if (movieDB.isEmpty()) {
            aiyo.print("\nNo movies in database...yet\n\n");
            return;
        }
        int counter = 0;
        Movie curr = movieDB.get(counter);
        while (counter <= (movieDB.size() - 1)) {
            curr = movieDB.get(counter);
            displayMovie(curr);
            counter++;
        }
        aiyo.readString("\nPlease hit enter to continue");
    }

    //displays movie's info
    public void displayMovie(Movie wantedMovie) {
        aiyo.print("ID: " + wantedMovie.getID());
        aiyo.print("Title: " + wantedMovie.getTitle());
        aiyo.print("Released on: " + wantedMovie.getReleaseDate());
        aiyo.print("MPAA Rating: " + wantedMovie.getRating());
        aiyo.print("Directed by: " + wantedMovie.getDirector());
        aiyo.print("Studio: " + wantedMovie.getStudio());
        aiyo.print("Comments: " + wantedMovie.getUserRating() + "\n\n");
    }

    public String getKeyword() {
        String keyWord = aiyo.readString("Please enter the keyword you would like to search for");
        return keyWord;
    }

    /*
    Used when updating movie info. Added extra prints to make the user aware of what the previous
    information was. 
    Would have liked to implement a way to simply press enter and keep the same info,
    but wasn't sure how to go about doing that
    */
    public Movie updateMovieInfo(Movie movie) {
        aiyo.print("Change " + movie.getTitle() + " to what?");
        aiyo.print("[Re-enter " + movie.getTitle() + " even if there is no change]");
        movie.setTitle(aiyo.readString("New Title:"));
        
        aiyo.print("Change movie release date from " + movie.getReleaseDate() + " to what?");
        aiyo.print("[Re-enter " + movie.getReleaseDate() + " even if there is no change]");
        movie.setReleaseDate(aiyo.readString("New release date:"));
        
        aiyo.print("Change " + movie.getRating() + " to what?");
        aiyo.print("[Re-enter " + movie.getRating() + " even if there is no change]");
        movie.setRating(aiyo.readString("New MPAA rating:"));
        
        aiyo.print("Change directed by from " + movie.getDirector() + " to whom?");
        aiyo.print("[Re-enter " + movie.getDirector() + " even if there is no change]");
        movie.setDirector(aiyo.readString("Directed by:"));
        
        aiyo.print("Change studio from " + movie.getStudio() + " to what?");
        aiyo.print("[Re-enter " + movie.getStudio() + " even if there is no change]");
        movie.setStudio(aiyo.readString("New studio:"));
        
        aiyo.print("Change user comments from: \n" + movie.getUserRating() + "\n to what?");
        aiyo.print("[Re-enter previous comments even if there is no change]");
        movie.setUserRating(aiyo.readString("New user comments: "));
        
        return movie;
    }

}
