/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.models;

import com.sluciak.dentistoffice.service.OpenAppointment;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author TomTom
 */
public class Appointment{
    private Patient patient;
    private Professional professional;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate date;
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
    
    
    public LocalDateTime getStartTime() {
        return startTime;
    }

    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    
    public LocalDateTime getEndTime() {
        return endTime;
    }

    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getLengthOfAppt() {
        return calculateLengthOfAppt();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = calculateTotalCost();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void update(OpenAppointment other){
        //may need to fill this in later
        System.out.println("fill this in my boy (update in Appointment class)");
    }
    
    private BigDecimal calculateTotalCost(){
        BigDecimal totalCost = new BigDecimal(calculateLengthOfAppt().toHours());
        totalCost = totalCost.multiply(professional.getHourlyRate());
        totalCost.setScale(2, RoundingMode.HALF_DOWN);
        return totalCost;
    }

    private Duration calculateLengthOfAppt() {
        return Duration.between(this.startTime, this.endTime);
    }
}
