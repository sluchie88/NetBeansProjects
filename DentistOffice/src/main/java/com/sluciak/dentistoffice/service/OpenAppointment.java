/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.models.Professional;
import java.time.LocalDateTime;

/**
 *
 * @author TomTom
 */
public class OpenAppointment {
    
    private Professional professional;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public OpenAppointment(){
    }
    
    public OpenAppointment(Professional doc, LocalDateTime sTime, LocalDateTime eTime){
        this.professional = doc;
        this.startTime = sTime;
        this.endTime = eTime;
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
    
    public void update(OpenAppointment other) {
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
