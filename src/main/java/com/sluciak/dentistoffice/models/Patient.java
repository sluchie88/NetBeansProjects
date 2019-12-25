/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author TomTom
 */
public class Patient {

    private int patientID;
    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public Patient() {

    }

    public Patient(String fName, String lName, LocalDate bDay) {
        this.firstName = fName;
        this.lastName = lName;
        this.birthday = bDay;
    }

    public Patient(int id, String fName, String lName, LocalDate bDay) {
        this.patientID = id;
        this.firstName = fName;
        this.lastName = lName;
        this.birthday = bDay;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

}
