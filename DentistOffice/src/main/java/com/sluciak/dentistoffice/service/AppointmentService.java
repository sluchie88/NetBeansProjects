/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.AppointmentFileDao;

/**
 *
 * @author TomTom
 */
public class AppointmentService implements AppointmentServiceInterface{

    private final AppointmentFileDao apptDao;
    
    public AppointmentService(AppointmentFileDao appointmentDao) {
        this.apptDao = appointmentDao;
    }
    
}
