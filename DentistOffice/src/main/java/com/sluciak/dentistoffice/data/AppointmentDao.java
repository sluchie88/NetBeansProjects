/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professions;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface AppointmentDao {
    
    public List<Appointment> findByProfessionalAndDate(LocalDate date, String lastName) throws Exception;
    
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException;
    
    public List<Appointment> findByDateAndPatient(LocalDate date, int id) throws StorageException;

    public Appointment updateAppointment(LocalDate date, Appointment old, Appointment newInfo)throws StorageException, Exception;
    
    public boolean cancelAppointment(LocalDate date, Appointment toCancel)throws StorageException;

    public Appointment addAppointment(LocalDate date, Appointment appt) throws StorageException;
    

}
