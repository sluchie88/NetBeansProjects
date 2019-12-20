/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface PatientDao {
    public List<Patient> findAll() throws StorageException;
    
    public List<Patient> findByBirthdate(LocalDate bDay) throws StorageException;
    
    public List<Patient> findByLastName(String lName) throws StorageException;
    
    Patient add(Patient patient) throws StorageException;
}
