/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.models.Professional;
import java.time.LocalTime;

/**
 *
 * @author TomTom
 */
public class TimeSlot {
    
    private Professional professional;
    private LocalTime startTime;
    private LocalTime endTime;
    
    public TimeSlot(){
    }
    
    public TimeSlot(Professional doc, LocalTime sTime, LocalTime eTime){
        this.professional = doc;
        this.startTime = sTime;
        this.endTime = eTime;
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
    
    public void update(TimeSlot other) {
	this.startTime = other.startTime;
        this.endTime = other.endTime;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    
}
