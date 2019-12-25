/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author TomTom
 */
public class PatientFileDaoStub implements PatientDao{

    private final ArrayList<Patient> patients = new ArrayList<>();

    public PatientFileDaoStub() {
        patients.add(new Patient(1, "Kearny", "Estabrook", LocalDate.of(1942, Month.SEPTEMBER, 19)));
        patients.add(new Patient(2, "Alejoa", "Lathom", LocalDate.of(1982, Month.AUGUST, 06)));
        patients.add(new Patient(3, "Gretchen", "Guiso", LocalDate.of(1929, Month.JUNE, 28)));
        patients.add(new Patient(4, "Sigmund", "Lossman", LocalDate.of(2013, Month.JULY, 21)));
        patients.add(new Patient(5, "Emily", "Gatecliff", LocalDate.of(1931, Month.JANUARY, 07)));
        patients.add(new Patient(6, "Loy", "Adamiec", LocalDate.of(1979, Month.JANUARY, 10)));
        patients.add(new Patient(7, "Kerr", "Worman", LocalDate.of(1922, Month.JULY, 28)));
    }


    @Test
    public List<Patient> findByBirthdate(LocalDate bDay) throws StorageException {
       return findAll().stream().filter(p -> p.getBirthday().equals(bDay)).collect(Collectors.toList());  
    }

    /**
     * Test of findByLastName method, of class PatientFileDao.
     */
    @Test
    public List<Patient> findByLastName(String lName) throws StorageException {
        List<Patient> allPats = findAll();
        List<Patient> foundPats = new ArrayList<>();
        for(Patient p : allPats){
            if(p.getLastName().equals(lName)){
                foundPats.add(p);
            }
        }
        if(!foundPats.isEmpty()){
            return foundPats;
        }else{
            return null;
        }
    }

    /**
     * Test of add method, of class PatientFileDao.
     */
    @Test
    public Patient add(Patient patient) throws StorageException {
        if (isNotADuplicate(patient)) {
            int maxID = findAll().stream()
                    .mapToInt(p -> p.getPatientID()).max().orElse(0);

            patient.setPatientID(maxID + 1);

            patients.add(patient);
            return patient;
        }
        throw new StorageException("Patient already exists.");
    }

    /**
     * Test of findByID method, of class PatientFileDao.
     */
    @Test
    public Patient findByID(int id) throws Exception {
        return findAll().stream()
                .filter(pat -> pat.getPatientID() == id)
                .findFirst().orElse(null);
    }

    
    private boolean isNotADuplicate(Patient patient) throws StorageException {
        List<Patient> allPats = findAll();
        
        
        for (Patient p : allPats) {
            if (patient.getFirstName().equalsIgnoreCase(p.getFirstName())
                    && patient.getLastName().equalsIgnoreCase(p.getLastName())
                    && patient.getBirthday().equals(p.getBirthday())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Patient> findAll() throws StorageException {
        return new ArrayList<>(patients);
    }
}
