/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.models.Professional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class ProfessionalFileDao implements ProfessionalDao {

    public static final String DELIMETER = ",";
    public static final String HEADER = "id,first_name,last_name,specialty,hourly_rate";

    String path;

    public ProfessionalFileDao(String path) {
        this.path = path;
    }

    @Override
    public List<Professional> findAll() throws StorageException {
        return readFile();
    }

    @Override
    public List<Professional> findByProfession(Professions prof) throws StorageException {
        return findAll().stream()
                .filter(p -> p.getSpecialty().equals(prof))
                .collect(Collectors.toList());
    }

    @Override
    public Professional findByLastName(String lastName) throws StorageException {
        return findAll().stream().filter(p -> p.getLastName().contains(lastName)).findAny().orElse(null);
    }

    @Override
    public Professional findById(int id) throws StorageException {
        return findAll().stream().filter(p -> p.getProfessionalID() == id).findAny().orElse(null);
    }

    private String mapToString(Professional prof) {
        String rate = prof.getHourlyRate().toString();
        return String.format("%s,%s,%s,%s,%s",
                prof.getProfessionalID(),
                prof.getFirstName(),
                prof.getLastName(),
                prof.getSpecialty().getJobTitle(),
                rate);
    }

    private Professional mapToProfessional(String intake) {
        String[] tokens = intake.split(DELIMETER);
        return new Professional(
                Integer.parseInt(tokens[0]),
                tokens[1],
                tokens[2],
                Professions.fromString(tokens[3]),
                new BigDecimal(tokens[4]));
    }

    /*
    refactor to stop using the fileDao class, and write methods to specifically
    read/write/append for these classes
     */
    private List<Professional> readFile() throws StorageException {

        List<Professional> staffRoster = new ArrayList<>();
        Scanner scanny;

        try {
            scanny = new Scanner(new BufferedReader(new FileReader(path)));
        } catch (FileNotFoundException fnfe) {
            throw new StorageException("File not found.");
        }
        //should skip the header
        String currLine = scanny.nextLine();
        Professional currProf;
        while (scanny.hasNextLine()) {
            currLine = scanny.nextLine();
            currProf = mapToProfessional(currLine);
            staffRoster.add(currProf);
        }
        scanny.close();
        return staffRoster;
    }

    private void writeAllProfessionals(String path, List<Professional> listy) throws IOException {
        try (PrintWriter tolkein = new PrintWriter(new FileWriter(path))) {
            tolkein.println(HEADER);
            for (Professional p : listy) {
                tolkein.println(mapToString(p));
            }
        }
    }
}
