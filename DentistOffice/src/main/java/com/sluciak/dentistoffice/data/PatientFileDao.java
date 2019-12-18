/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class PatientFileDao
        extends FileDao
        implements PatientDao {

    public PatientFileDao(String path) {
        super(path, 4, true);
    }

    @Override
    public List<Patient> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        try{
            
        }catch(IOException ioe){
            throw new StorageException("File not found");
        }
    }

    @Override
    public boolean update(Patient patient) throws StorageException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String mapToString(Patient patient){
        return String.format("%s,%s,%s,%s", 
                patient.getPatientID(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthday());
    }
    
    private Patient mapToPatient(String[] tokens){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/DD/YYYY");
        LocalDate dob = LocalDate.parse(tokens[3], formatter);
        return new Patient(
                Integer.getInteger(tokens[0]),//ID
                tokens[1],//FirstName
                tokens[2],//LastName
                dob);//DateofBirth
    }
}
