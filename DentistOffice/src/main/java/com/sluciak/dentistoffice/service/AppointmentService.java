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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
        return apptDao.findByProfession(date, job);
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
    public TimeSlot cancelAppointment(Appointment toCancel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    /*
    Business Hours
    Monday-Friday: 7:30AM-12:30PM, then 1:00PM-6:00PM
    Saturday: 8:30AM-12:30PM
    Scheduling

    NEVER allow an Appointment outside of business hours.
    DENTIST: min Appointment is 15 minutes, max 3 hours
    HYGIENIST: min 30 minutes, max 2 hours
    ORTHODONTIST: min 15 minutes, max 1 hour
    ORAL_SURGEON: min 30 minutes, max 8 hours (may schedule over lunch if Appointment > 5 hours)
    Only one Appointment per Customer, per Specialty, per day. (A Customer could see a HYGIENIST 
    in the morning and a DENTIST in the afternoon, but never two DENTISTs in a single day.)
    
    It is absolutely essential to prevent Dental Professionals from being double-booked.
     */
    public List<TimeSlot> findOpenAppointments(LocalDate dateOfChoice, Professions title) throws StorageException {
        LocalTime openWk = LocalTime.of(7, 30);
        LocalTime closeWk = LocalTime.of(18, 0);
        LocalTime openWe = LocalTime.of(8, 30);
        LocalTime closeWe = LocalTime.of(12, 30);

        List<Appointment> allApptsForDate = findByProfession(dateOfChoice, title);
        //allApptsForDate.sort((a, b) -> a.getStartTime().compareTo(b.getStartTime()));
        List<TimeSlot> allOpenTimes = new ArrayList<>();

        /*
        Think like a linked list. Hold onto previous appointment for comparison's sake
        Compare end time of previous appointment to start time of next appointment
         */
        for (int i = 0; i < allApptsForDate.size(); i++) {
            Appointment curr = allApptsForDate.get(i);
            Appointment nCurr = allApptsForDate.get(i + 1);

            //general case
            if (curr.getProfessional().getProfessionalID() == nCurr.getProfessional().getProfessionalID()) {
                TimeSlot oa = new TimeSlot();
                //checks for start and end times the same. no open appointment here
                if (curr.getEndTime().compareTo(nCurr.getStartTime()) == 0) {

                } //checks for gap between start and end time
                else if (curr.getEndTime().compareTo(nCurr.getStartTime()) < 0) {
                    oa.setProfessional(curr.getProfessional());
                    oa.setStartTime(curr.getEndTime());
                    oa.setEndTime(nCurr.getStartTime());
                    allOpenTimes.add(oa);
                }
            } else {

            }

        }
        return allOpenTimes;
    }

    private boolean isWeekend(LocalDate date) {
        return !(date.getDayOfWeek().equals(DayOfWeek.MONDAY)
                || date.getDayOfWeek().equals(DayOfWeek.TUESDAY)
                || date.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)
                || date.getDayOfWeek().equals(DayOfWeek.THURSDAY)
                || date.getDayOfWeek().equals(DayOfWeek.FRIDAY));
    }

    private TimeSlot findGap(Appointment first, Appointment second, boolean isWeekend) {

        TimeSlot opening = new TimeSlot();
        
        LocalTime closeWe = LocalTime.of(12, 30);

        LocalTime openWk = LocalTime.of(7, 30);
        LocalTime closeWk = LocalTime.of(18, 0);
        
        //checks first appointment in list that it is at opening time
        if (first.getProfessional().getProfessionalID() != second.getProfessional().getProfessionalID()) {
            //checks
            if (isWeekend) {
                LocalTime openWe = LocalTime.of(8, 30);
                if (second.getStartTime().compareTo(openWe) > 0) {
                    opening.setProfessional(second.getProfessional());
                    opening.setStartTime(openWe);
                    opening.setEndTime(second.getStartTime());
                }
            } else {

            }
        } else {

        }

    }
}
