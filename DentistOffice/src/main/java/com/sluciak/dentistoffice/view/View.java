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
import com.sluciak.dentistoffice.service.OpenAppointment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author TomTom
 */
public class View {

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

        aiyo.print(String.format("%s, %s : %s : %s |  %s ",
                apt.getProfessional().getLastName(),
                apt.getProfessional().getSpecialty().getJobTitle(),
                apt.getPatient().getLastName(),
                apt.getStartTime(), apt.getEndTime()));
    }

    public void displayOpenAppointments(OpenAppointment oa) {
        aiyo.print(String.format("%s, %s.  %s | %s", oa.getProfessional().getLastName(), oa.getProfessional().getSpecialty().getJobTitle(),
                oa.getStartTime(), oa.getEndTime()));
        ;
    }

    public void displayProfessional(Professional pro) {
        aiyo.print(pro.getFirstName() + " "
                + pro.getLastName() + " "
                + pro.getSpecialty().getJobTitle());
    }

    public void displayPatient(Patient p) {
        aiyo.print(p.getFirstName() + " "
                + p.getLastName()
                + ". DOB: " + p.getBirthday());

    }

    public Patient makePatient() {
        printHeader("New Patient");
        Patient patient = new Patient();
        patient.setFirstName(enterFirstName());
        patient.setLastName(enterLastName());
        patient.setBirthday(LocalDate.parse(enterBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        return patient;
    }

    public LocalDate enterDate(String prompt) {
        aiyo.print(prompt);
        LocalDate date = LocalDate.parse(aiyo.readString("Enter a date in the format MM/DD/YYYY "), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return date;
    }

    public String enterLastName() {
        return aiyo.readString("Enter the last name of the person: ");
    }

    public String enterFirstName() {
        return aiyo.readString("Enter the first name of the person: ");
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



}
