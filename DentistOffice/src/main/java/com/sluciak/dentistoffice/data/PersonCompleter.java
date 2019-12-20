/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import java.util.List;

/**
 *
 * @author TomTom
 */
/*
personCompleter class
	sits in data layer
	can see all three daos
	completes information based on incomplete Prof/Patient in appointment
 */
public class PersonCompleter {

    private static ProfessionalFileDao professionalDao = new ProfessionalFileDao("professionals.txt");
    private static PatientFileDao patientDao = new PatientFileDao("patients.txt");

    /*
    going to need to call methods in patient and professional dao's to find 
    patient and professional based on information in the appointment
     */
    public static Professional getProfessionalByLastName(String token) {
        try {
            List<Professional> found = professionalDao.findByLastName(token);
            return found.get(0);
        } catch (StorageException se) {
            return new Professional();
        }
    }

    public static Patient getPatientByID(int ID) {
        try {
            List<Patient> found = patientDao.findByID(ID);
            return found.get(0);
        } catch (StorageException se) {
            return new Patient();
        }
    }
}
