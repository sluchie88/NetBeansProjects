/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import static com.sluciak.dentistoffice.data.ProfessionalFileDao.HEADER;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class PatientFileDao implements PatientDao {

    String path;
    public static final String DELIMETER = ",";
    public static final String HEADER = "id,first_name,last_name,dob";

    public PatientFileDao(String path) {
        this.path = path;
    }

    @Override
    public List<Patient> findAll() throws StorageException {
        List<Patient> patients = readFile();
        if (!patients.isEmpty()) {
            return patients;
        } else {
            throw new StorageException("No patients have that birthday");
        }
    }

    @Override
    public List<Patient> findByBirthdate(LocalDate bDay) throws StorageException {
        return findAll().stream().filter(p -> p.getBirthday().equals(bDay)).collect(Collectors.toList());
    }

    @Override
    //should be able to search with just a portion of a last name now...
    public List<Patient> findByLastName(String lName) throws StorageException {
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1, (lName.length()));

        List<Patient> allPats = findAll();
        List<Patient> foundPats = new ArrayList<>();
        for (Patient p : allPats) {
            if (p.getLastName().contains(lName)) {
                foundPats.add(p);
            }
        }
        if (!foundPats.isEmpty()) {
            return foundPats;
        } else {
            return null;
        }
    }

    @Override
    public Patient add(Patient patient) throws StorageException {
        if (isNotADuplicate(patient)) {
            int maxID = findAll().stream()
                    .mapToInt(p -> p.getPatientID()).max().orElse(0);

            patient.setPatientID(maxID + 1);

            List<Patient> allPats = readFile();
            allPats.add(patient);
            try{
                writeAllPatients(allPats);
            }catch(IOException ioe){
                throw new StorageException("Unable to write new patient to file.");
            }
            return patient;

        }
        return null;
    }

    public Patient findByID(int id) throws Exception {
        return findAll().stream()
                .filter(pat -> pat.getPatientID() == id)
                .findFirst().orElse(null);
    }

    private String mapToString(Patient patient) {
        String parsedDate = patient.getBirthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return String.format("%s,%s,%s,%s",
                patient.getPatientID(),
                patient.getFirstName(),
                patient.getLastName(),
                parsedDate);
    }

    private Patient mapToPatient(String intake) {
        String[] tokens = intake.split(DELIMETER);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(tokens[3], formatter);
        return new Patient(
                Integer.parseInt(tokens[0]),//ID
                tokens[1],//FirstName
                tokens[2],//LastName
                dob);//DateofBirth
    }

    private boolean isNotADuplicate(Patient patient) {
        boolean isNotADupe = true;
        List<Patient> allPats;
        try {
            allPats = findAll();
        } catch (StorageException ex) {
            return isNotADupe;
        }
        for (Patient p : allPats) {
            if (patient.getFirstName().equalsIgnoreCase(p.getFirstName())
                    && patient.getLastName().equalsIgnoreCase(p.getLastName())
                    && patient.getBirthday().equals(p.getBirthday())) {
                isNotADupe = false;
                break;
            }
        }
        return isNotADupe;
    }

    /*
    refactor to stop using the fileDao class, and write methods to specifically
    read/write/append for these classes
     */
    private List<Patient> readFile() throws StorageException {
        List<Patient> patientList = new ArrayList<>();
        Scanner scanny;

        try {
            scanny = new Scanner(new BufferedReader(new FileReader(path)));
        } catch (FileNotFoundException fnfe) {
            throw new StorageException("File not found.");
        }
        //should skip the header
        String currLine = scanny.nextLine();
        Patient currPat;
        while (scanny.hasNextLine()) {
            currLine = scanny.nextLine();
            currPat = mapToPatient(currLine);
            patientList.add(currPat);
        }
        scanny.close();
        return patientList;
    }

    private void writeAllPatients(List<Patient> listy) throws IOException {
        try (PrintWriter erikson = new PrintWriter(new FileWriter(path))) {
            erikson.println(HEADER);
            for (Patient p : listy) {
                erikson.println(mapToString(p));
            }
        }
    }

}
