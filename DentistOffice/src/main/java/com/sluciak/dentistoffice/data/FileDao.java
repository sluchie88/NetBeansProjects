/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author TomTom
 */
public abstract class FileDao<T> {

    private final String path;
    private final int colCount;
    private final boolean hasHeaders;

    /*
    constructor
    allows for general contructor because using three different file formats. will need
    to load in professionals, appointments, and patients
     */
    public FileDao(String path, int columnCount, boolean hasHeaders) {
        this.path = path;
        this.colCount = columnCount;
        this.hasHeaders = hasHeaders;
    }

    /*
    read function for varying files. it's a thing of beauty and still blows my mind.
    And no, I'm not trying to brown-nose
     */
    public List<T> read(Function<String[], T> mapper) throws StorageException {
        ArrayList<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            if (hasHeaders) {
                reader.readLine();
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == colCount) {
                    T object = mapper.apply(tokens);
                    if (object != null) {
                        result.add(object);
                    }
                }
            }
        } catch (IOException ex) {
            throw new StorageException(ex.getMessage(), ex);
        }
        return result;
    }

    /*
    Generalized function for writing to a file.
     */
    public void write(Collection<T> items, Function<T, String> mapper) throws StorageException {
        try(PrintWriter gutenberg = new PrintWriter(path)){
            for(T object : items){
                if(object != null){
                    gutenberg.println(mapper.apply(object));
                }
            }
        }catch (IOException ex){
            throw new StorageException(ex.getMessage(), ex);
        }
    }
    
    /*
    adds a new item to the end of the file
    */
    public void append(T item, Function<T, String> mapper) throws StorageException{
        try(PrintWriter gutenberg = new PrintWriter(new FileWriter(path, true))){
            gutenberg.println(mapper.apply(item));
        }catch(IOException ex){
            throw new StorageException(ex.getMessage(), ex);
        }
    }
}
