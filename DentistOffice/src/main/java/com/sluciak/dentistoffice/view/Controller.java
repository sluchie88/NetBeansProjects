/*
NOTES TO SELF:
Remaining tasks
 1. Add complexity to searching for open appointments (i.e. lunch)
 2. Add time contraints according to profession for making a new appointment
 3. Add check that Appointment file exists, if not make one
 4. Figure out how to handle TimeSlots for a new date
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.view;

import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.service.AppointmentService;
import com.sluciak.dentistoffice.service.ErrorMessage;
import com.sluciak.dentistoffice.service.TimeSlot;
import com.sluciak.dentistoffice.service.PersonService;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class Controller<T> {

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
                    viewAppointmentByPatient();
                    break;
                case SCHEDULE_APPT:
                    scheduleNewAppointment();
                    break;
                case UPDATE_APPT:
                    updateAppointment();
                    break;
                case CREATE_PATIENT:
                    createNewPatient();
                    break;
                case CANCEL_APPT:
                    cancelAppointment();
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
        String ans;
        String profLastName = getLastNameForSearch("Enter at least the last name of the staff member you are searching for: ");
        ErrorMessage britta = new ErrorMessage();
        //private method that gets and verifies the date the user wishes to search for
        LocalDate date = getDateForSearch();

        britta = personService.professionalExists(profLastName);
        if (britta.hasError()) {
            view.displayErrorMessage(britta);
        } else {
            try {
                List<Appointment> appts = apptService.findByDrAndDate(date, profLastName);
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
        String promptAnswer;
        String ynAnswer = "y";
        ErrorMessage ohNo = new ErrorMessage();

        //private method that gets and verifies the date the user wishes to search for
        LocalDate date = getDateForSearch();

        //takes in patient last name being searched for
        String patLastName = getLastNameForSearch("Enter at least the first 3 letter of the last name of the patient you are searching for: ");

        //possible to DRY this method up? used in most controller methods. Abstract method?
        //displays a menu for the user and returns their choice
        List<Patient> matches = personService.findPatientByLastName(patLastName);

        //replaced methods specific to object with general method for displaying 
        //search results and getting user choice
        int menuChoice = getMenuSelection(matches);

        Patient patient = matches.get(menuChoice);

        List<Appointment> appts = null;
        //go into the appointment dao and find the
        try {
            appts = apptService.findByDateAndPatient(date, patient.getPatientID());
        } catch (StorageException se) {
            ohNo.addErrors(se.getMessage());
        }
        if (!ohNo.hasError()) {
            view.displayMessage(patient.getFirstName() + " " + patient.getLastName() + "'s appointment details:");
            for (int i = 0; i < appts.size(); i++) {
                view.displayAppointment(appts.get(i));
            }
        } else {
            view.displayErrorMessage(ohNo);
        }
    }

    /*
    Enter a date.
    Choose a Customer.
    Choose an Appointment.
    Allow a Dental Professional or User to add notes to the Appointment or change its total cost
    
    issue with displaying appointment info. need to show cost and notes
     */
    public void updateAppointment() {
        String promptAnswer;
        String ynAnswer = "y";
        ErrorMessage oopsieDaisy = new ErrorMessage();

        //private method that gets and verifies the date the user wishes to search for
        LocalDate date = getDateForSearch();

        //takes in patient last name being searched for
        String patLastName = getLastNameForSearch("Enter at least the first 3 letter of the last name of the patient you are searching for: ");

        //gets a list of patients with matching last names, then asks user to pick the right one
        List<Patient> matches = personService.findPatientByLastName(patLastName);
        int menuChoice = getMenuSelection(matches);
        Patient patient = matches.get(menuChoice);

        //begins by finding all appointments for the provided date by pillaging the appointment dao
        List<Appointment> appts = null;
        try {
            appts = apptService.findByDateAndPatient(date, patient.getPatientID());
        } catch (StorageException se) {
            oopsieDaisy.addErrors(se.getMessage());
            view.displayErrorMessage(oopsieDaisy);
        }

        //initial check to make sure appointments were actually found
        if (appts != null && appts.size() > 0) {
            //display menu function
            for (int i = 0; i < appts.size(); i++) {
                view.displayAppointmentInMenu(appts.get(i), i);
            }
            menuChoice = view.readChoiceOfOptions("Enter the number of the appointment you would like to edit.") - 1;
            Appointment edited = appts.get(menuChoice);
            edited = view.displayAndGetAppointmentInformation(edited);

            oopsieDaisy = apptService.updateAppointment(date, edited);
            if (oopsieDaisy.hasError()) {
                view.displayErrorMessage(oopsieDaisy);
            } else {
                view.printSuccess("Appointment updated successfully!");
            }

        } else {
            oopsieDaisy.addErrors("Unable to locate any appointments on the date given. Please try agian with another day or patient.");
            view.displayErrorMessage(oopsieDaisy);
        }
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

    //edit, but same basic logic as updating an appointment
    public void cancelAppointment() {
        String promptAnswer;
        String ynAnswer = "y";
        ErrorMessage yaDunGoofed = new ErrorMessage();

        //private method that gets and verifies the date the user wishes to search for
        LocalDate date = getDateForSearch();

        //takes in patient last name being searched for
        String patLastName = getLastNameForSearch("Enter at least the first 3 letter of the last name of the patient you are searching for: ");

        List<Patient> matches = personService.findPatientByLastName(patLastName);

        int menuChoice = getMenuSelection(matches);

        //gets user menu choice
        Patient patient = matches.get(menuChoice);

        List<Appointment> appts = null;
        //go into the appointment dao and find the
        try {
            appts = apptService.findByDateAndPatient(date, patient.getPatientID());
        } catch (StorageException se) {
            yaDunGoofed.addErrors(se.getMessage());
            view.displayErrorMessage(yaDunGoofed);
        }
        if (appts != null && appts.size() > 0) {
            //display menu function
            for (int i = 0; i < appts.size(); i++) {
                view.displayAppointmentInMenu(appts.get(i), i);
            }
            menuChoice = view.readChoiceOfOptions("Enter the number of the appointment being canceled.") - 1;
            Appointment cenceling = appts.get(menuChoice);

            yaDunGoofed = apptService.cancelAppointment(date, cenceling);
            if (yaDunGoofed.hasError()) {
                view.displayErrorMessage(yaDunGoofed);
            } else {
                view.printSuccess("Appointment canceled successfully!");
            }

        } else {
            yaDunGoofed.addErrors("Unable to locate any appointments on the date given. Please try agian with another day or patient.");
            view.displayErrorMessage(yaDunGoofed);
        }
    }

    /**
     * ***********************
     * Private Methods Below *
     * ***********************
     */
    //method that asks user for the patient's last name
    private Patient getPatientFromUser() {
        String answer = "n";
        String patientLastName;
        ErrorMessage woops = new ErrorMessage();
        Patient patient = new Patient();

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
                return new Patient();
            }
        } else {
            patientLastName = view.enterLastName("What is the last name of the patient?");
            List<Patient> patsLname = personService.findPatientByLastName(patientLastName);
            int menuChoice = getMenuSelection(patsLname);
            patient = patsLname.get(menuChoice);
        }
        return patient;
    }

    //method that asks user to choose a profession from the menu
    private Professions getProfessionFromUser() {
        Professions jorb;
        String answer;
        do {
            int pro = view.displayAndGetChoiceProfession();
            jorb = Professions.fromValue(pro);
            answer = view.readYesNoPrompt("You chose " + Professions.fromValue(pro) + ", is this correct?");
        } while (answer.equalsIgnoreCase("n"));
        return jorb;
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

    private LocalDate getDateForSearch() {
        LocalDate date = null;
        String promptAnswer;
        String ynAnswer = "n";

        //takes in date being searched for
        do {
            promptAnswer = view.enterDate("Enter the date you would like to look at: ");
            if (isValidDate(promptAnswer)) {
                ynAnswer = view.readYesNoPrompt("You entered " + promptAnswer + ", is this correct?");
                date = formatDate(promptAnswer);
            } else {
                view.displayMessage("Date is incorrect format, please try again.");
            }
        } while (ynAnswer.equalsIgnoreCase("n"));
        return date;
    }

    private String getLastNameForSearch(String prompt) {
        String lastName = null;
        String ynAnswer = "n";

        ErrorMessage uhoh = new ErrorMessage();
        do {
            lastName = view.enterLastName(prompt);
            uhoh = personService.confirmValidName(lastName);
            if (uhoh.hasError()) {
                view.displayErrorMessage(uhoh);
                uhoh.deleteErrors(uhoh);
            } else {
                ynAnswer = view.readYesNoPrompt("You entered " + lastName + ", is this correct?");
            }
        } while (ynAnswer.equalsIgnoreCase("n"));
        return lastName;
    }

    /*
    I had this big ugly monster of a method sitting in the middle of my code. Thought it looked better
    here
    
    User may choose from an existing Customer or add a new Customer ("Add a Customer" use case).
    Enter a date.
    Enter a Specialty.
    Application shows available time slots for all Dental Professionals with that Specialty.
    Choose a Dental Professional.
    Enter a start and end time for the Appointment.
    Review/confirm. If the user doesn't confirm, the Appointment must not be saved.
    
    Factored into methods, but now having other issues
        Need to fix grabbing user info and initial patient options (new or existing)
        Run to make sure
        Need to add options for creating a new appointment or choosing from open times
        Adding for a new date will get...complicated. Need to map it out first
     */
    public void scheduleNewAppointment() {
        int menuChoice = 0;
        String answer;
        Patient patient = new Patient();
        ErrorMessage woops = new ErrorMessage();

        //should run until a proper answer is given, being either y or n
        do {
            answer = view.readYesNoPrompt("Is this appointment for an existing patient? "
                    + "\nEnter [y] to search, [n] to create a new patient");
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
        if (answer.equalsIgnoreCase("n")) {
            patient = createNewPatientForAppointment();
        } else {
            //bit of extra legwork here but ultimately retrieves desired patient from Dao
            String patientLastName = getLastNameForSearch("Enter at least the first 3 letter of the last name of the patient you are searching for: ");
            List<Patient> matches = personService.findPatientByLastName(patientLastName);
            menuChoice = getMenuSelection(matches);
            patient = matches.get(menuChoice);
        }
        //gets profession sought
        Professions jorb = getProfessionFromUser();

        /*
        need to check times for all professionals, even if they don't exist in
        the appointment file. this is the list of all, but not sure how to use it
         */
        List<Professional> allProfs = null;
        try {
            allProfs = personService.findByProfession(jorb);
        } catch (StorageException se) {
            woops.addErrors(se.getMessage());
        }
        LocalDate dateOfChoice;
        TimeSlot choiceOfOpenAppt = new TimeSlot();
        List<TimeSlot> openings = null;
        boolean keepRunning;

        //loop for entering date and seeing available appointments
        do {
            keepRunning = true;
            dateOfChoice = getDateForSearch();
            boolean weekend = dateOfChoice.getDayOfWeek().equals(DayOfWeek.SATURDAY);

            //checks date to make sure there are appointments
            try {
                openings = apptService.findOpenAppointments(dateOfChoice, jorb);
            } catch (StorageException se) {
                woops.addErrors(se.getMessage());
            }

            openings = personService.addMissingProfessionals(openings, allProfs, weekend);

            try {
                choiceOfOpenAppt = findAndDisplayOpenAppointments(openings, dateOfChoice);
                if (choiceOfOpenAppt != null) {
                    keepRunning = false;
                }
            } catch (NullPointerException npe) {
                view.printFormat("No open appointments on this date. Please try a new day.");
            }
        } while (keepRunning);

        if (!addAppointmentToFile(dateOfChoice, choiceOfOpenAppt, patient)) {
            view.printSuccess("Appointment successfully added.");
        } else {
            woops.addErrors("There was an error adding the appointment. Please try again.");
            view.displayErrorMessage(woops);
        }

    }

    private Patient createNewPatientForAppointment() {
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
            return null;
        } else {
            view.printSuccess(pat.getFirstName() + " added!");
            return pat;
        }
    }

    private TimeSlot findAndDisplayOpenAppointments(List<TimeSlot> openings, LocalDate dateOfChoice) {

        int menuChoice;
        String answer;
        ErrorMessage goshDarnit = new ErrorMessage();

        //need to do something to check out whether or not
        //the date file exists. From there can prompt user for inputting an entirely
        //new appointment for a new date
        //this is not working. need to retool
        view.printFormat(String.format("%s, %s.  %s | %s", "Name of professional", "Profession", "Start of opening", "End of opening"));
        for (int i = 0; i < openings.size(); i++) {
            view.displayOpenAppointments(openings.get(i), i + 1);
        }
        menuChoice = view.readChoiceOfOptions("Enter the number of the appointment that works best: \nEnter -1 if you wish to search for a new date.");
        if (menuChoice == -1) {
            return null;
        } else {
            view.printFormat("You chose " + menuChoice + "\n");
            view.displayOpenAppointments(openings.get(menuChoice - 1), menuChoice);
            answer = view.readYesNoPrompt("is this correct?");
            return openings.get(menuChoice - 1);
        }
    }

    private boolean addAppointmentToFile(LocalDate date, TimeSlot openSlot, Patient patient) {
        LocalTime startTime;
        LocalTime endTime;
        Appointment appt = new Appointment();

        if (openSlot.getProfessional().getSpecialty() != Professions.ORAL_SURGEON) {
            startTime = getStartTimeNotOralSurg(openSlot);
            endTime = getEndTimeNotOralSurg(openSlot);
        } else {
            startTime = getStartTimeOralSurg(openSlot);
            endTime = getEndTimeOralSurg(openSlot);
        }
        appt.setStartTime(startTime);
        appt.setEndTime(endTime);

        String notes = view.getDoctorsNotes();
        BigDecimal totalCost = calculateTotalCostOfAppt(appt, openSlot.getProfessional().getHourlyRate());

        appt.setProfessional(openSlot.getProfessional());
        appt.setPatient(patient);

        appt.setTotalCost(totalCost);
        appt.setNotes(notes);

        ErrorMessage uhoh = apptService.addAppointment(date, appt);
        return uhoh.hasError();
    }

    /*
    Was having issues with this method. For whatever reason declaring total cost separately
    as the hourly rate, then doing the multiplication stopped it from being 0. Not sure why
    
    This method calculates the total cost for an appointment after getting the start
    and end times.
     */
    private BigDecimal calculateTotalCostOfAppt(Appointment appt, BigDecimal hourlyRate) {
        Duration amtTime = Duration.between(appt.getStartTime(), appt.getEndTime());
        long totalMins = amtTime.toMinutes();
        BigDecimal totalCost = BigDecimal.valueOf(totalMins).multiply(hourlyRate);
        totalCost = totalCost.divide(new BigDecimal("60")).setScale(2);
        //switch(totalMins)

        return totalCost;
    }

    /*
    factored out methods for getting times from user. Made separate methods for Oral
    Surgeons because the checks on the time for start/end are different due to lunch
     */
    private LocalTime getStartTimeNotOralSurg(TimeSlot opening) {
        LocalTime startTime = null;
        String timeEntry;
        do {
            timeEntry = view.readStartEndTime("Start Time");
            if (isValidTime(timeEntry)) {
                startTime = formatTime(timeEntry);
            }

        } while (startTime == null || startTime.isBefore(opening.getStartTime()));
        return startTime;
    }

    private LocalTime getEndTimeNotOralSurg(TimeSlot opening) {
        LocalTime endTime = null;
        String timeEntry;
        do {
            timeEntry = view.readStartEndTime("End Time");
            if (isValidTime(timeEntry)) {
                endTime = formatTime(timeEntry);
            }

        } while (endTime == null || endTime.isAfter(opening.getEndTime()));
        return endTime;
    }

    private LocalTime getStartTimeOralSurg(TimeSlot opening) {
        LocalTime startTime = null;
        String timeEntry;
        do {
            timeEntry = view.readStartEndTime("Start Time");
            if (isValidTime(timeEntry)) {
                startTime = formatTime(timeEntry);
            }

        } while (startTime == null);
        return startTime;
    }

    private LocalTime getEndTimeOralSurg(TimeSlot opening) {
        LocalTime endTime = null;
        String timeEntry;
        do {
            timeEntry = view.readStartEndTime("=== End Time ===");
            if (isValidTime(timeEntry)) {
                endTime = formatTime(timeEntry);
            }

        } while (endTime == null);
        return endTime;
    }

    /*
    Idea is to create an abstract method that will display items from any type of list and 
    allow the user to select from them. Issue is passing to the view since
    patient has different fields than a professional than an appointment than
    a timeslot. Need to investigate, not imperative that this works
    
    public abstract class MenuDisplay<T> {

        public int getMenuSelection(List<T> objs) {
            for (int i = 0; i < objs.size(); i++) {
                view.displayObject(objs.get(i), i);
            }
            //gets user menu choice
            int menuChoice = view.readChoiceOfOptions("Enter the number of the patient you would like to select.") - 1;
            Patient patient = objs.get(menuChoice);
        }
    }
    
    
    As per Corbin's suggestion, changed from abstract to generic method. Much nicer
     */
    public <T> int getMenuSelection(List<T> listy) {
        return view.displayMenuAndReadChoiceOfOptions(listy);
    }
}
