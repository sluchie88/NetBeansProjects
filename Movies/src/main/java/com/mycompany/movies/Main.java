/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movies;

import com.mycompany.movies.controller.Controller;
import com.mycompany.movies.dao.Dao;
import com.mycompany.movies.dao.DaoException;
import com.mycompany.movies.dao.DaoImpl;
import com.mycompany.movies.ui.MovieListView;
import com.mycompany.movies.ui.UserIO;
import com.mycompany.movies.ui.UserIOImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TomTom
 */
public class Main {
    public static void main(String[] args) {
        UserIO io = new UserIOImpl();
        MovieListView view = new MovieListView(io);
        Dao dd = new DaoImpl();
        try {
            //this actually loads in all the movies from the file
            //was going to make a load method but it seemed redundant when
            //I could just call this here
            dd.getAllMovies();
        } catch (DaoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Controller conty = new Controller(dd, view);
        conty.run();
    }
}


/*
public class App {

    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl();
        ClassRosterView myView = new ClassRosterView(myIo);
        ClassRosterDao myDao = new ClassRosterDaoFileImpl();
        ClassRosterController controller = new ClassRosterController(myDao, myView);
        controller.run();
    }

}
*/