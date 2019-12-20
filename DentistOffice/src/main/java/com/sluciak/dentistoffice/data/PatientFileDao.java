/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        return readObject(this::mapToPatient).stream().collect(Collectors.toList());

    }

    @Override
    public List<Patient> findByBirthdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Patient> findByLastName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Patient add(Patient patient) throws StorageException {
        
        int maxID = findAll().stream()
                .mapToInt(p -> p.getPatientID()).max().orElse(0);

        patient.setPatientID(maxID + 1);

        appendObject(patient, this::mapToString);
        return patient;
    }
    
    public List<Patient> findByID(int parseInt) throws StorageException{
        return readObject(this::mapToPatient).stream()
                .filter(pat -> pat.getPatientID() == parseInt)
                .collect(Collectors.toList());
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

    
}
