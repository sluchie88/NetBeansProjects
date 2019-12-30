/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class PatientFileDao
        extends FileDao<Patient>
        implements PatientDao {

    public PatientFileDao(String path) {
        super(path, 4, true);
    }

    @Override
    public List<Patient> findAll() throws StorageException {
        List<Patient> patients = readObject(this::mapToPatient).stream().collect(Collectors.toList());
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

            appendObject(patient, this::mapToString);
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

    private Patient mapToPatient(String[] tokens) {
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
}
