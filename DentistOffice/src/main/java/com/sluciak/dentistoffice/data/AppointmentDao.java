/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.service.TimeSlot;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface AppointmentDao {
    
    public List<Appointment> findByProfessionalAndDate(LocalDate date, String lastName) throws StorageException;
    
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException;
    
    public List<Appointment> findByDateAndPatient(LocalDate date, String lastName) throws StorageException;

    public Appointment updateAppointment(Appointment change);
    
    public TimeSlot cancelAppointment(Appointment toCancel);
    

}
