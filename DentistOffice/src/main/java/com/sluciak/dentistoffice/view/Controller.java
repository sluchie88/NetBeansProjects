/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.view;

import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.service.AppointmentService;
import com.sluciak.dentistoffice.service.ErrorMessage;
import com.sluciak.dentistoffice.service.TimeSlot;
import com.sluciak.dentistoffice.service.PersonService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

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
                    //viewAppointmentByPatient();
                    break;
                case SCHEDULE_APPT:
                    scheduleNewAppointment();
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

    /*
    need to display the list of doctors and let user choose from list. entering dr.
    name is not very user friendly
     */
    public void displayApptsByDateAndDoctor() {
        String date;
        String ans;
        String profName;
        ErrorMessage britta = new ErrorMessage();
        do {
            date = view.enterDate("Enter the date you would like to check:");
            ans = view.readYesNoPrompt("You entered " + date + ", is this correct?");
        } while (!isValidDate(date) || ans.equalsIgnoreCase("n"));
        do {
            profName = view.enterFirstName("Enter the last name of the staff member: ");
            ans = view.readYesNoPrompt("You entered " + profName + ", is this correct?");
        } while (ans.equalsIgnoreCase("n"));
        britta = personService.professionalExists(profName);
        if (britta.hasError()) {
            view.displayErrorMessage(britta);
        } else {
            try {
                List<Appointment> appts = apptService.findByDrAndDate(formatDate(date), profName);
                view.printFormat(String.format("%s, %s : %s : %s |  %s ",
                        "Patient",
                        "Seeing",
                        "Specialty",
                        "Starts",
                        "Ends"));
                for (Appointment a : appts) {
                    view.displayAppointment(a);
                }
            } catch (StorageException se) {
                britta.addErrors(se.getMessage());
                view.displayErrorMessage(britta);
            }
        }
    }

    /*
    Enter a date.
    Choose a Customer.
    Application displays Appointments or indicates there are none.
    Choose an Appointment.
    Application displays full Appointment details.
     */
    public void viewAppointmentByPatient() {

    }

    /*
    User may choose from an existing Customer or add a new Customer ("Add a Customer" use case).
    Enter a date.
    Enter a Specialty.
    Application shows available time slots for all Dental Professionals with that Specialty.
    Choose a Dental Professional.
    Enter a start and end time for the Appointment.
    Review/confirm. If the user doesn't confirm, the Appointment must not be saved.
     */
    public void scheduleNewAppointment() {
        int menuChoice = 0;
        Patient patient;
        ErrorMessage woops = new ErrorMessage();
        String patientLastName;
        String answer = view.readYesNoPrompt("Is this appointment for an existing patient? "
                + "\nEnter [y] to search, [n] to create a new patient");

        if (answer.equalsIgnoreCase("n")) {
            do {
                patient = view.makePatient();
                patientLastName = patient.getLastName();
                view.displayPatient(patient);
                answer = view.readYesNoPrompt("Is this information correct?");
            } while (answer.equalsIgnoreCase("n"));
            woops = personService.addNewPatient(patient);
            if (woops.hasError()) {
                view.displayErrorMessage(woops);
                return;
            }
        } else {
            patientLastName = view.enterLastName("What is the last name of the patient?");
            List<Patient> patsLname = personService.findPatientByLastName(patientLastName);
            menuChoice = view.displayAndGetChoicePatient(patsLname) - 1;
            patient = patsLname.get(menuChoice);
        }

        //gets profession sought
        do {
            int pro = view.displayAndGetChoiceProfession();
            answer = view.readYesNoPrompt("You chose " + Professions.fromValue(pro) + ", is this correct?");
        } while (answer.equalsIgnoreCase("n"));

        //loop for entering date and seeing available appointments
        do {
            LocalDate dateOfChoice = getDateForAppt();
            try {
                List<TimeSlot> openings = apptService.findOpenAppointments(dateOfChoice);
            } catch (StorageException se) {
                woops.addErrors(se.getMessage());
            }

            //need to get choice somehow
            answer = "n";
            if (menuChoice == 0) {
                answer = view.readYesNoPrompt("Search for another date?");
            }

        } while (answer.equalsIgnoreCase("y"));

        /*
        for displaying open appointments
        view.printFormat(String.format("%s, %s.  %s | %s", "Name of professional", "Profession", "Start of opening", "End of opening"));
         */
    }

    /*
    Enter a date.
    Choose a Customer.
    Choose an Appointment.
    Allow a Dental Professional or User to add notes to the Appointment or change its total cost
     */
    public void updateAppointment() {

    }

    /*
    try to add a case where user can re-enter info if something is wrong or an error is found
    
    
    need to add condition so user confirms adding, or abandons
     */
    public void createNewPatient() {
        Patient pat = new Patient();
        String ans;
        ErrorMessage bungled = new ErrorMessage();
        do {
            pat = view.makePatient();
            String bDay = view.enterBirthdate();
            if (isValidDate(bDay)) {
                pat.setBirthday(formatDate(bDay));
            }
            view.displayPatient(pat);
            ans = view.readYesNoPrompt("Is this information correct?");
            bungled = personService.addNewPatient(pat);
        } while (!ans.equalsIgnoreCase("y"));

        if (bungled.hasError()) {
            view.displayErrorMessage(bungled);
        } else {
            view.printSuccess(pat.getFirstName() + " added!");
        }
    }

    public void cancelAppointment() {

    }

    private boolean isValidDate(String date) {
        LocalDate day = formatDate(date);
        return day != null;
    }

    private boolean isValidTime(String time) {
        LocalTime ldt = formatTime(time);
        return ldt != null;
    }

    private LocalDate formatDate(String date) {
        LocalDate parsed;
        try {
            parsed = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            return parsed;
        } catch (DateTimeParseException dtpe) {
            return null;
        }
    }

    private LocalTime formatTime(String time) {
        LocalTime parsed;
        try {
            parsed = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return parsed;
        } catch (DateTimeParseException dtpe) {
            return null;
        }
    }

    private LocalDate getDateForAppt() {
        String date = view.enterDate("Enter the date the user wishes to schedule for:");
        return formatDate(date);
    }
}
