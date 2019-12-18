/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.PatientFileDao;
import com.sluciak.dentistoffice.data.ProfessionalFileDao;
import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Patient;

/**
 *
 * @author TomTom
 */
public class PersonService implements PersonServiceInterface{

    private final PatientFileDao patDaon;
    private final ProfessionalFileDao proDao;
    
    public PersonService(PatientFileDao patientDao, ProfessionalFileDao professionalDao) {
        this.patDaon = patientDao;
        this.proDao = professionalDao;
    }

    public Result<Patient> addNewPatient(Patient pat) {
        Result<Patient> goofed = new Result<>();
        if(Validation.isNullOrWhiteSpace(pat.getFirstName())){
            goofed.addErrors("Patient must have a first name.");
        }
        if(Validation.isNullOrWhiteSpace(pat.getLastName())){
            goofed.addErrors("Patient must have a last name.");
        }
        if(Validation.isNullOrWhiteSpace("" + pat.getBirthday())){
            goofed.addErrors("Patient must have a birthdate.");
        }
        if(goofed.hasError()){
            return goofed;
        }
        
        try{
            pat = patDaon.add(pat);
            goofed.setValue(pat);
        }catch(StorageException ex){
            goofed.addErrors(ex.getMessage());
        }
        return goofed;
    }
    
}
