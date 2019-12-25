/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author TomTom
 */
public class PersonCompleterStub {
    private ProfessionalFileDaoStub profDao = new ProfessionalFileDaoStub();
    private PatientFileDaoStub patDao = new PatientFileDaoStub();
    private AppointmentFileDaoStub apptDao = new AppointmentFileDaoStub();
     
    public PersonCompleterStub() {
    }
        
    public Professional testGetProfessionalByLastName(String lastName) throws StorageException {
        return profDao.findByLastName(lastName);
    }

    
    public Patient testGetPatientByID(int id) throws Exception {
        return patDao.findByID(id);
    }
    
}
