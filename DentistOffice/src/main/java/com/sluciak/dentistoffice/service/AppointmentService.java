/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.AppointmentFileDao;
import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Professions;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class AppointmentService implements AppointmentServiceInterface {

    //private final String APPOINTMENT_FILE_PATH;
    private final AppointmentFileDao apptDao;

    public AppointmentService(AppointmentFileDao appointmentDao) {
        this.apptDao = appointmentDao;
    }

    @Override
    public List<Appointment> findByDrAndDate(LocalDate date, String drName) throws StorageException {
        return apptDao.findByProfessionalAndDate(date, drName);
    }

    @Override
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> findByDateAndPatient(LocalDate date, String lastName) throws StorageException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Appointment updateAppointment(Appointment change) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OpenAppointment cancelAppointment(Appointment toCancel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
