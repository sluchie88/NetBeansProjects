/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.view;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.service.ErrorMessage;
import com.sluciak.dentistoffice.service.TimeSlot;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class View<T> {

    private final UserIO aiyo;

    public View(UserIO uio) {
        this.aiyo = uio;
    }

    public MainMenuOptions readMainMenuSelection() {
        int min = 0;
        int max = 6;
        for (MainMenuOptions mainO : MainMenuOptions.values()) {
            aiyo.print(String.format("%s. %s", mainO.getValue(), mainO.getName()));
            min = Math.min(mainO.getValue(), min);
            max = Math.max(mainO.getValue(), max);
        }
        int value = aiyo.readInt(String.format("Select [%s-%s]", min, max));
        return MainMenuOptions.fromValue(value);
    }

    public void displayAppointment(Appointment apt) {

        aiyo.print(String.format("%s, %s : %s : %s |  %s \n",
                apt.getProfessional().getLastName(),
                apt.getProfessional().getSpecialty().getJobTitle(),
                apt.getPatient().getLastName(),
                apt.getStartTime(), apt.getEndTime()));
    }

    public void displayOpenAppointments(TimeSlot oa, int count) {
        aiyo.print(String.format(count + ". %s, %s.  %s | %s", oa.getProfessional().getLastName(),
                oa.getProfessional().getSpecialty().getJobTitle(),
                oa.getStartTime(), oa.getEndTime()));
    }

    public int readChoiceOfOptions(String prompt) {
        return aiyo.readInt(prompt);
    }

    public void displayProfessional(Professional pro) {
        aiyo.print(pro.getFirstName() + " "
                + pro.getLastName() + " "
                + pro.getSpecialty().getJobTitle());
    }

    public void displayPatient(Patient p) {
        String dob = "";
        if (p.getBirthday() != null) {
            dob += p.getBirthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        aiyo.print(p.getFirstName() + " "
                + p.getLastName() + ". DOB: " + dob);
        //                + ". DOB: " + p.getBirthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

    }

    public Patient makePatient() {
        printHeader("New Patient");
        Patient patient = new Patient();
        patient.setFirstName(enterFirstName("Enter the first name of the patient: "));
        patient.setLastName(enterLastName("Enter the last name of the patient: "));
        return patient;
    }

    public String enterDate(String prompt) {
        aiyo.print(prompt);
        return aiyo.readString("Enter a date in the format MM/DD/YYYY ");
    }

    public String enterLastName(String prompt) {
        return aiyo.readString(prompt);
    }

    public String enterFirstName(String prompt) {
        return aiyo.readString(prompt);
    }

    public String enterBirthdate() {
        return aiyo.readString("Enter birthdate in the format MM/DD/YYYY ");
    }

    //added in the plus one to value and minus one to readInt since the enum requires
    //starting at 0. seemed easier for the user to do 1 to 4 rather than 0 to 3
    public Professions readProfession() {
        printHeader("Choose type of professional");
        for (Professions pro : Professions.values()) {
            aiyo.print(String.format("%s. %s", pro.getValue() + 1, pro.getJobTitle()));
        }
        int val = aiyo.readInt("Select [1-4]") - 1;
        return Professions.fromValue(val);
    }

    public String readTime() {
        printHeader("Enter a time");
        return aiyo.readString("Enter a time in 24 hour format, using HH:mm");
    }
    
    public String readStartEndTime(String prompt){
        printHeader(prompt);
        return aiyo.readString("Enter a time in 24 hour format, using HH:mm");
    }

    public void goodbye() {
        printHeader("Goodbye!");
        aiyo.print("Have a nice day!");
    }

    public void displayErrorMessage(ErrorMessage error) {
        aiyo.print("");
        printHeader("ERROR");
        for (String msg : error.getErrors()) {
            aiyo.print(msg);
        }
        aiyo.print("");
    }

    public String readYesNoPrompt(String message) {
        aiyo.print(message);
        return aiyo.readString("Enter [y/n]:");
    }

    public void printFormat(String format) {
        aiyo.print(format);
    }

    private void printHeader(String message) {
        aiyo.print(String.format("===== %s =====", message));
    }

    public void printSuccess(String string) {
        printHeader("Success");
        aiyo.print(string);
    }

    public int displayAndGetChoicePatient(List<Patient> patList) {
        int min = 1;
        int max = 1;
        for (int i = 0; i < patList.size(); i++) {
            System.out.print(i + 1 + ". ");
            displayPatient(patList.get(i));
            max++;
        }
        return aiyo.readInt("Enter the number of the paitent you would like to choose [" + min + "-" + max + "]");
    }

    public int displayAndGetChoiceProfessional(List<Professional> proList) {
        int min = 1;
        int max = 1;
        for (int i = 0; i < proList.size(); i++) {
            System.out.print(i + 1);
            displayProfessional(proList.get(i));
            max++;
        }
        return aiyo.readInt("Enter the number of the paitent you would like to choose [" + min + "-" + max + "]");
    }

    public int displayAndGetChoiceAppointment(List<Appointment> apptList) {
        int min = 1;
        int max = 1;
        for (int i = 0; i < apptList.size(); i++) {
            System.out.print(i + 1);
            displayAppointment(apptList.get(i));
            max++;
        }
        return aiyo.readInt("Enter the number of the paitent you would like to choose [" + min + "-" + max + "]");
    }

    public int displayAndGetChoiceProfession() {
        aiyo.print("1. " + Professions.DENTIST.toString());
        aiyo.print("2. " + Professions.ORTHODONTIST.toString());
        aiyo.print("3. " + Professions.HYGIENIST.toString());
        aiyo.print("4. " + Professions.ORAL_SURGEON.toString());
        return (aiyo.readInt("Enter [1-4] for which type of professional the patient would like to see.") - 1);
    }

    public String getDoctorsNotes() {
        return aiyo.readString("Please enter any notes you would like to add to the appointment. If none, enter n/a");
    }

    public void displayMessage(String prompt) {
        aiyo.print(prompt);
    }

    public void displayPatientInMenu(Patient get, int i) {
        System.out.print((i + 1) + ". ");
        displayPatient(get);
    }

    public Appointment displayAndGetAppointmentInformation(Appointment appt) {
        aiyo.print("This is the current appointment information:");
        displayAppointmentWithCostNotes(appt);
        String ynAns = readYesNoPrompt("Would you like to edit the cost?");
        if (ynAns.equalsIgnoreCase("y")) {
            aiyo.print("Current cost: $" + appt.getTotalCost());
            BigDecimal newCost = new BigDecimal(aiyo.readString("New cost: "));
            appt.setTotalCost(newCost);
        }
        ynAns = readYesNoPrompt("Would you like to edit/add notes?");
        if (ynAns.equalsIgnoreCase("y")) {
            if (appt.getNotes() != null || !appt.getNotes().isEmpty()) {
                aiyo.print("Current notes: " + appt.getNotes());
            }
            String newNotes = aiyo.readString("New notes: ");
            appt.setNotes(newNotes);
        }
        return appt;
    }

    public void displayAppointmentWithCostNotes(Appointment apt) {
        aiyo.print(String.format("%s, %s : %s : $ %s : %s |  %s \n Notes: " + apt.getNotes(),
                apt.getProfessional().getLastName(),
                apt.getProfessional().getSpecialty().getJobTitle(),
                apt.getPatient().getLastName(),
                apt.getTotalCost(),
                apt.getStartTime(), apt.getEndTime()));
    }

    void displayAppointmentInMenu(Appointment apt, int i) {
        System.out.print((i + 1) + ". ");
        aiyo.print(String.format("%s, %s : %s : $ %s : %s |  %s \n Notes: " + apt.getNotes(),
                apt.getProfessional().getLastName(),
                apt.getProfessional().getSpecialty().getJobTitle(),
                apt.getPatient().getLastName(),
                apt.getTotalCost(),
                apt.getStartTime(), apt.getEndTime()));
    }

    
    /*
    override toString method for each object type
    */
    public int displayMenuAndReadChoiceOfOptions(List<T> listy) {
        for(int i = 0; i < listy.size(); i++){
            aiyo.print("[" + (i + 1) + "] " + listy.get(i).toString());
        }
        return ((aiyo.readInt("Enter the number of the option you want above: ")) - 1);
    }
}
