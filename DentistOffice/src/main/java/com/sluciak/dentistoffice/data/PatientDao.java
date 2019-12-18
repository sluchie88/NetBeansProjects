/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface PatientDao {
    public List<Patient> findAll();
    
    public List<Patient> findByBirthdate();
    
    public List<Patient> findByLastName();
    
    Patient add(Patient patient) throws StorageException;
    
    boolean update(Patient patient) throws StorageException;
}
