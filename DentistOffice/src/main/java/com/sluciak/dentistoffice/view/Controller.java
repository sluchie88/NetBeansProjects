/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.view;

import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.service.AppointmentService;
import com.sluciak.dentistoffice.service.ErrorMessage;
import com.sluciak.dentistoffice.service.PersonService;
import java.time.LocalDate;

/**
 *
 * @author TomTom
 */
public class Controller {

    private final View view;
    private final AppointmentService apptService;
    private final PersonService personService;

    public Controller(View view, AppointmentService apptService, PersonService personService) {
        this.view = view;
        this.apptService = apptService;
        this.personService = personService;
    }

    public void run() {
        boolean keepRunning = true;
        MainMenuOptions menuSelection;
        do {
            menuSelection = view.readMainMenuSelection();
            switch (menuSelection) {
                case DISPLAY_BY_DR_DATE:
                    displayApptsByDateAndDoctor();
                    break;
                case VIEW_APPT:
                    //viewAppointmentByCustomer();
                    break;
                case SCHEDULE_APPT:
                    //scheduleNewAppointment();
                    break;
                case UPDATE_APPT:
                    //updateAppointment();
                    break;
                case CREATE_PATIENT:
                    createNewPatient();
                    break;
                case CANCEL_APPT:
                    //cancelAppointment();
                    break;
            }
        } while (menuSelection != MainMenuOptions.EXIT);
        view.goodbye();
    }

    public void displayApptsByDateAndDoctor() {
        LocalDate lookingFor = view.enterDate("Enter the date you would like to check:");

        view.printFormat(String.format("%s, %s : %s : %s |  %s ", "Name of professional", "Profession", "Patient last name", "Start time", "End time"));
    }

    public void viewAppointmentByCustomer() {

    }

    public void scheduleNewAppointment() {

        /*
        for displaying open appointments
        view.printFormat(String.format("%s, %s.  %s | %s", "Name of professional", "Profession", "Start of opening", "End of opening"));
         */
    }

    public void updateAppointment() {

    }

    public void createNewPatient() {
        Patient pat = new Patient();
        String ans;
        do {
            pat = view.makePatient();
            view.displayPatient(pat);
            ans = view.readYesNoPrompt("Is this information correct?");
        } while (!ans.equalsIgnoreCase("y"));
        ErrorMessage bungled = new ErrorMessage();
        if(!bungled.hasError()){
            view.printSuccess(pat.getFirstName() + " added!");
        }
        else{
            view.displayErrorMessage(bungled);
        }
    }

    public void cancelAppointment() {

    }
}
