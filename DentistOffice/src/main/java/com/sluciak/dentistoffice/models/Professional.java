/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.models;

import java.math.BigDecimal;

/**
 *
 * @author TomTom
 */
public class Professional {
    private int professionalID;
    private String firstName;
    private String lastName;
    private Professions specialty;
    private BigDecimal hourlyRate;
    
    public Professional(){
        
    }
    
    public Professional(String fName, String lName, Professions job, BigDecimal rate){
        this.firstName = fName;
        this.lastName = lName;
        this.specialty = job;
        this.hourlyRate = rate;
    }
    
    public Professional(int Id, String fName, String lName, Professions job, BigDecimal rate){
        this.professionalID = Id;
        this.firstName = fName;
        this.lastName = lName;
        this.specialty = job;
        this.hourlyRate = rate;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
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

    public Professions getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Professions specialty) {
        this.specialty = specialty;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    /*
    first name
    last name
    specialty
    hourly rate
    */
    @Override
    public String toString(){
        return String.format("%s %s: %s. $%s an hour.", 
                this.firstName,
                this.lastName,
                this.specialty.getJobTitle(),
                this.hourlyRate);
    }
}
