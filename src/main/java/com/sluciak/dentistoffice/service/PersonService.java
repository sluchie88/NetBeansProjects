/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.PatientDao;
import com.sluciak.dentistoffice.data.ProfessionalDao;
import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class PersonService implements PersonServiceInterface {

    private final PatientDao patDaon;
    private final ProfessionalDao proDao;

    public PersonService(PatientDao patientDao, ProfessionalDao professionalDao) {
        this.patDaon = patientDao;
        this.proDao = professionalDao;
    }

    public Outcome<Patient> addNewPatient(Patient pat) {
        Outcome<Patient> goofed = new Outcome<>();
        if (Validation.isNullOrWhiteSpace(pat.getFirstName())) {
            goofed.addErrors("Patient must have a first name.");
        }
        if (Validation.isNullOrWhiteSpace(pat.getLastName())) {
            goofed.addErrors("Patient must have a last name.");
        }
        if (pat.getBirthday() == null) {
            goofed.addErrors("Patient must have a birthdate.");
        }
        if (!isNotADuplicate(pat)) {
            goofed.addErrors("Duplicate. Patient with same name and birthdate already exists.");
        }
        if (goofed.hasError()) {
            return goofed;
        }

        try {
            pat = patDaon.add(pat);
            goofed.setValue(pat);
        } catch (StorageException ex) {
            goofed.addErrors(ex.getMessage());
        }
        return goofed;
    }

    public Outcome<Professional> professionalExists(String lName) {
        Outcome<Professional> mistake = new Outcome<>();
        try {
            if (proDao.findByLastName(lName) == null) {
                mistake.addErrors(lName + " does not exist in this system");
            }
        } catch (StorageException se) {
            mistake.addErrors(se.getMessage());
        }
        return mistake;
    }

    private boolean isNotADuplicate(Patient patient) {
        boolean isNotADupe = true;
        List<Patient> allPats;
        try {
            allPats = patDaon.findAll();
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

    public boolean findProfByLastName(String lName) {
        try {
            return proDao.findByLastName(lName) != null;
        } catch (StorageException se) {
            return false;
        }
    }

    public List<Patient> findPatientByLastName(String patientLastName) {
        try {
            return patDaon.findByLastName(patientLastName);
        } catch (StorageException se) {
            System.out.println(se.getMessage());
            return null;
        }
    }

    public ErrorMessage confirmValidName(String promptAnswer) {
        ErrorMessage dunderhead = new ErrorMessage();
        if (promptAnswer == null || promptAnswer.isBlank() || promptAnswer.isEmpty()) {
            dunderhead.addErrors("Field cannot be empty. Please enter all or part of a patient's last name.");
        } else {
            char[] boop = promptAnswer.toCharArray();
            for (char c : boop) {
                if (Character.isDigit(c)) {
                    dunderhead.addErrors("Names can't contain any numbers. Please try again.");
                    break;
                }
            }
        }
        return dunderhead;
    }
}
