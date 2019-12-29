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
    private int patID;
    private Professional professional;
    private String profName;
    private Professions title;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal totalCost;
    private String notes;
    
    public Appointment(){
        
    }

    public Appointment(LocalTime startTime, LocalTime endTime, BigDecimal cost){

        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = cost;
    }
    
    public Appointment(LocalTime startTime, LocalTime endTime, BigDecimal cost, String notes){
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = cost;
        this.notes = notes;
    }
    
    public Appointment(int patID, String drName, Professions title, LocalTime sTime, LocalTime eTime, BigDecimal cost){
        this.patID = patID;
        this.profName = drName;
        this.title = title;
        this.startTime = sTime;
        this.endTime = eTime;
    }
    
    public Appointment(int patID, String drName, Professions title, LocalTime sTime, LocalTime eTime, BigDecimal cost, String notes){
        this.patID = patID;
        this.profName = drName;
        this.title = title;
        this.startTime = sTime;
        this.endTime = eTime;
        this.notes = notes;
    }
    
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
    
    /*
    patient last name
    doctor last name
    start time
    end time
    total cost
    notes
    */
    @Override
    public String toString(){
        return String.format("%s seeing %s. %s | %s; $%s\n NOTES: %s", 
                this.patient.getLastName(),
                this.profName,
                this.startTime,
                this.endTime,
                this.totalCost,
                this.notes);
    }
    
}
