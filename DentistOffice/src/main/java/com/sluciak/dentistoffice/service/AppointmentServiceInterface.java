/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Professions;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface AppointmentServiceInterface {

    public List<Appointment> findByDrAndDate(LocalDate date, String drName) throws StorageException;

    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException;

   public List<Appointment> findByDateAndPatient(LocalDate date, int id) throws StorageException;

    public Appointment updateAppointment(LocalDate date, Appointment old, Appointment updated);

    public boolean cancelAppointment(LocalDate date, Appointment toCancel);

    public List<TimeSlot> findOpenAppointments(LocalDate dateOfChoice, Professions title) throws StorageException;

}
