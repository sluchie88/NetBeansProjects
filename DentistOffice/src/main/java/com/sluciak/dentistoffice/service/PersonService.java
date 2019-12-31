/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.PatientDao;
import com.sluciak.dentistoffice.data.ProfessionalDao;
import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public List<Professional> findByProfession(Professions profession) throws StorageException {
        List<Professional> allProfs = proDao.findByProfession(profession);
        if (allProfs != null && !allProfs.isEmpty()) {
            return allProfs;
        } else {
            throw new StorageException("Unable to locate any staff of that profession.");
        }
    }

    /*
    takes in the list of all found openings and checks if there are any professionals not listed
    If any professionals don't have appointments yet, they will be added as open all day
     */
    public List<TimeSlot> addMissingProfessionals(List<TimeSlot> openings, List<Professional> allProfs, boolean weekend) {
        if (!Validation.isEmptyList(openings)) {
            for (Professional p : allProfs) {
                boolean found = false;
                for (TimeSlot ts : openings) {
                    if (ts.getProfessional().getLastName().equals(p.getLastName())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (weekend) {
                        openings.add(new TimeSlot(p, LocalTime.of(8, 30), LocalTime.of(12, 30)));
                    } else {
                        openings.add(new TimeSlot(p, LocalTime.of(7, 30), LocalTime.of(12, 30)));
                        openings.add(new TimeSlot(p, LocalTime.of(13, 00), LocalTime.of(18, 00)));
                    }
                }
            }
            return openings;
        } else {
            openings = new ArrayList<>(); 
            for (Professional p : allProfs) {
                if (weekend) {
                    openings.add(new TimeSlot(p, LocalTime.of(8, 30), LocalTime.of(12, 30)));
                } else {
                    openings.add(new TimeSlot(p, LocalTime.of(7, 30), LocalTime.of(12, 30)));
                    openings.add(new TimeSlot(p, LocalTime.of(13, 00), LocalTime.of(18, 00)));
                }
            }
            return openings;
        }
    }
}

/*

 */
