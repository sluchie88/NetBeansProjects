/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.AppointmentDao;
import com.sluciak.dentistoffice.data.StorageException;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
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
    private final AppointmentDao apptDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.apptDao = appointmentDao;
    }

    Object addAppointment(Appointment apptN) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> findByDrAndDate(LocalDate date, String drName) throws StorageException {
        try {
            return apptDao.findByProfessionalAndDate(date, drName);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException {
        return apptDao.findByProfession(date, job);
    }

    @Override
    public List<Appointment> findByDateAndPatient(LocalDate date, int id) throws StorageException {
        return apptDao.findByDateAndPatient(date, id);
    }

    @Override
    public Appointment updateAppointment(LocalDate date, Appointment old, Appointment updated) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cancelAppointment(LocalDate date, Appointment toCancel) {
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

        List<Appointment> allApptsForDate = findByProfession(dateOfChoice, title);
        //allApptsForDate.sort((a, b) -> a.getStartTime().compareTo(b.getStartTime()));
        List<TimeSlot> allOpenTimes = new ArrayList<>();

        /*
        Think like a linked list. Hold onto previous appointment for comparison's sake
        Compare end time of previous appointment to start time of next appointment
         */
        LocalTime openWE = LocalTime.of(8, 30);
        LocalTime closeWE = LocalTime.of(12, 30);
        LocalTime openWD = LocalTime.of(7, 30);
        LocalTime closeWD = LocalTime.of(18, 00);
        TimeSlot opening = new TimeSlot();

        if (dateOfChoice.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new StorageException("We are not open on Sundays. Please pick a new day.");
        }
        //keeps making my code break and say no open appointments
//        if(allOpenTimes.isEmpty()){
//            throw new StorageException("There are no open appointments for this day. Please try another date.");
//        }
        //checks for openings at the open time on the weekend
        if (isWeekend(dateOfChoice)) {
            allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(0), openWE));
            for (int i = 0; i < allApptsForDate.size(); i++) {
                try {
                    allApptsForDate.get(i + 1);
                } catch (IndexOutOfBoundsException ioobe) {
                    allOpenTimes.add(findGapEndOfDay(allApptsForDate.get(i), closeWE));
                    break;
                }
                if (allApptsForDate.get(i).getProfessional().getProfessionalID()
                        != allApptsForDate.get(i + 1).getProfessional().getProfessionalID()) {
                    allOpenTimes.add(findGapEndOfDay(allApptsForDate.get(i), closeWE));
                    allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(i + 1), openWE));
                } else {
                    allOpenTimes.add(findGap(allApptsForDate.get(i), allApptsForDate.get(i + 1)));
                }
            }
        } //route for appointments during the week
        else {
            allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(0), openWD));
            for (int i = 0; i < allApptsForDate.size(); i++) {
                try {
                    allApptsForDate.get(i + 1);
                } catch (IndexOutOfBoundsException ioobe) {
                    allOpenTimes.add(findGapEndOfDay(allApptsForDate.get(i), closeWE));
                    break;
                }
                if (allApptsForDate.get(i).getProfessional().getProfessionalID()
                        != allApptsForDate.get(i + 1).getProfessional().getProfessionalID()) {
                    allOpenTimes.add(findGapEndOfDay(allApptsForDate.get(i), closeWD));
                    allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(i + 1), openWD));
                } else {
                    allOpenTimes.add(findGap(allApptsForDate.get(i), allApptsForDate.get(i + 1)));
                }
            }
        }
        List<TimeSlot> cleaned = new ArrayList<>();
        for (int i = 0; i < allOpenTimes.size(); i++) {
            if (allOpenTimes.get(i) != null) {
                cleaned.add(allOpenTimes.get(i));
            }
        }
        //need to account for lunch
        return cleaned;
    }

    //checks if date lands on the weekend
    private boolean isWeekend(LocalDate date) {
        return !(date.getDayOfWeek().equals(DayOfWeek.MONDAY)
                || date.getDayOfWeek().equals(DayOfWeek.TUESDAY)
                || date.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)
                || date.getDayOfWeek().equals(DayOfWeek.THURSDAY)
                || date.getDayOfWeek().equals(DayOfWeek.FRIDAY));
    }

    /*
    finds a gap between two appointments. Called to if the professionals are the same.
    rewrote this several times. Originally had it split between weekday and weekend.
    then realized I could combine them by having the opening as a parameter. then realized it
    was stupid because I wasn't checking for end of day appointments....
    Me am smart.
     */
    private TimeSlot findGap(Appointment first, Appointment second) {

        TimeSlot opening = new TimeSlot();

        //checks first appointment in list that it is at opening time
        if (first.getEndTime().compareTo(second.getStartTime()) < 0) {
            opening.setStartTime(first.getEndTime());
            opening.setEndTime(second.getStartTime());
            opening.setProfessional(first.getProfessional());
            return opening;
        } else {
            return null;
        }
    }

    /*
    checks for open appointments at the end of the day
     */
    private TimeSlot findGapEndOfDay(Appointment appt, LocalTime close) {
        TimeSlot lastButLeast = new TimeSlot();
        if (appt.getEndTime().compareTo(close) < 0) {
            lastButLeast.setProfessional(appt.getProfessional());
            lastButLeast.setEndTime(close);
            lastButLeast.setStartTime(appt.getEndTime());
            return lastButLeast;
        } else {
            return null;
        }
    }

    /*
    checks for open appointments first thing in the morning
     */
    private TimeSlot findGapStartOfDay(Appointment appt, LocalTime open) {
        TimeSlot earlyBird = new TimeSlot();
        if (appt.getStartTime().compareTo(open) > 0) {
            earlyBird.setProfessional(appt.getProfessional());
            earlyBird.setStartTime(open);
            earlyBird.setEndTime(appt.getStartTime());
            return earlyBird;
        } else {
            return null;
        }
    }

    public ErrorMessage addNewAppointment(LocalDate date, Appointment appt) {
        ErrorMessage aiyah = new ErrorMessage();
        try {
            apptDao.addAppointment(date, appt);
        } catch (StorageException se) {
            aiyah.addErrors(se.getMessage());
        }
        return aiyah;
    }
}
