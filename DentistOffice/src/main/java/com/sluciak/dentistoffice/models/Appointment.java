/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.models;

import com.sluciak.dentistoffice.service.TimeSlot;
import java.math.BigDecimal;
import java.time.LocalTime;

/**
 *
 * @author TomTom
 */
public class Appointment{
    private Patient patient;
    private Professional professional;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal totalCost;
    private String notes;
    

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    
    public Professional getProfessional(){
        return professional;
    }
    
     
    public void setProfessional(Professional professional){
        this.professional = professional;
    }
    
    
    public LocalTime getStartTime() {
        return startTime;
    }

    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    
    public LocalTime getEndTime() {
        return endTime;
    }

    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void update(TimeSlot other){
        //may need to fill this in later
        System.out.println("fill this in my boy (update in Appointment class)");
    }
    
}
