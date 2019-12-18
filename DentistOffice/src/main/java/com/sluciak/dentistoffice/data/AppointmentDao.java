/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.service.OpenAppointment;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface AppointmentDao {
    
    public List<Appointment> findAll();

    public List<Appointment> findByDate(LocalDate day);

    public List<Appointment> findByDateAndPatient(LocalDate day, String lastName);
    
    public List<Appointment> findOpenAppointments(LocalDate day);

    public Appointment updateAppointment(Appointment change);
    
    public OpenAppointment cancelAppointment(Appointment toCancel);
}
