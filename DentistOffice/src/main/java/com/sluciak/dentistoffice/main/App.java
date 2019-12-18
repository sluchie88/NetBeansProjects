/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.main;

import com.sluciak.dentistoffice.data.AppointmentFileDao;
import com.sluciak.dentistoffice.data.PatientFileDao;
import com.sluciak.dentistoffice.data.ProfessionalFileDao;
import com.sluciak.dentistoffice.service.AppointmentService;
import com.sluciak.dentistoffice.service.PersonService;
import com.sluciak.dentistoffice.view.Controller;
import com.sluciak.dentistoffice.view.UserIO;
import com.sluciak.dentistoffice.view.View;


/**
 *
 * @author TomTom
 */
public class App {
    public static final String PROFESSIONAL_FILE_PATH = "professionals.txt";
    public static final String PATIENT_FILE_PATH = "patients.txt";
    
    public static void main(String[] args) {
        UserIO uio = new UserIO();
        View view = new View(uio);
        
        PatientFileDao patientDao = new PatientFileDao(PATIENT_FILE_PATH);
        AppointmentFileDao appointmentDao = new AppointmentFileDao();
        ProfessionalFileDao professionalDao = new ProfessionalFileDao(PROFESSIONAL_FILE_PATH);
        
        AppointmentService apptService = new AppointmentService(appointmentDao);
        PersonService personService = new PersonService(patientDao, professionalDao);
        Controller joyStick = new Controller(view, apptService, personService);
        
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("appContext.xml");
//        Controller joyStick = ctx.getBean("controller", Controller.class);
        
        joyStick.run();
    }
}
