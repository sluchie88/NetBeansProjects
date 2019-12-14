package org.g10.lottery.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class FileDao<T> {

    private final String path;
    private final int columnCount;
    private final boolean hasHeaders;

    /*
    constructor
    */
    public FileDao(String path, int columnCount, boolean hasHeaders) {
        this.path = path;
        this.columnCount = columnCount;
        this.hasHeaders = hasHeaders;
    }

    /*
    this function is reading in from the file and taking arguments as a Function...functional
    interface. Takes the array of strings, splits the strings on the comma, 
    changes them into a generic type and applys them to the needed object. Returns all
    of the objects in an ArrayList
    */
    public List<T> read(Function<String[], T> mapper) throws StorageException {

        ArrayList<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            if (hasHeaders) { // throw out header...
                reader.readLine();
            }

            String line;
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");

                if (tokens.length == columnCount) {
                    T obj = mapper.apply(tokens);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            }
        } catch (IOException ex) {
            throw new StorageException(ex.getMessage(), ex);
        }
        return result;
    }

    /*
    used for writing various objects (tickets and customers) to the file. Taken in as
    a collection, and for each object in the items collection, then passes the T objects
    into the function and changes them into a String, which is then written on a line
    in the text file.
    */
    public void write(Collection<T> items, Function<T, String> mapper) throws StorageException {
        try (PrintWriter writer = new PrintWriter(path)) {
            for (T obj : items) {
                if (obj != null) {
                    writer.println(mapper.apply(obj));
                }
            }
        } catch (IOException ex) {
            throw new StorageException(ex.getMessage(), ex);
        }
    }

    /*
    Not really sure what this is doing. Seems to also be writing to the file, but maybe
    individual items? porbably used to update single items in the file, but that's
    pure conjecture.
    Answer: it's used to add a single item to the file. Used in CustomerFileDao
    */
    public void append(T item, Function<T, String> mapper) throws StorageException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(mapper.apply(item));
        } catch (IOException ex) {
            throw new StorageException(ex.getMessage(), ex);
        }
    }

}
