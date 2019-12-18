/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

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
    private final AppointmentFileDao appointmentDao;
    private final ProfessionalFileDao professionalDao;
    private final PatientFileDao patientDao;
    
    public PersonCompleter(AppointmentFileDao appointmentDao, ProfessionalFileDao profDao, PatientFileDao patDao){
        this.appointmentDao = appointmentDao;
        this.professionalDao = profDao;
        this.patientDao = patDao;
    }
    
    /*
    going to need to call methods in patient and professional dao's to find 
    patient and professional based on information in the appointment
    */
}
