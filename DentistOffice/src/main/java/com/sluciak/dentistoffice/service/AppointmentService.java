/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.AppointmentDao;
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
    private final AppointmentDao apptDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.apptDao = appointmentDao;
    }

    @Override
    public ErrorMessage addAppointment(LocalDate date, Appointment apptN) {
        ErrorMessage chumbawumba = new ErrorMessage();

        if (Validation.appointmentIsOkay(date, apptN)) {
            try {
                apptDao.addAppointment(date, apptN);
            } catch (StorageException se) {
                chumbawumba.addErrors(se.getMessage());
            }
        }else{
            chumbawumba.addErrors("Something went wrong. Please make sure the appointment is during normal business hours and the appropriate length.");
        }
        return chumbawumba;
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
    public ErrorMessage updateAppointment(LocalDate date, Appointment updated) {
        ErrorMessage aiyah = new ErrorMessage();

        try {
            apptDao.updateAppointment(date, updated);
        } catch (StorageException se) {
            aiyah.addErrors(se.getMessage());
        }
        return aiyah;
    }

    @Override
    public ErrorMessage cancelAppointment(LocalDate date, Appointment toCancel) {
        ErrorMessage effinACotton = new ErrorMessage();
        try {
            //returns true of unable to remove appointment, false if removed successfully
            if (!apptDao.cancelAppointment(date, toCancel)) {

            } else {
                effinACotton.addErrors("Blahblahblah");
            }
        } catch (StorageException se) {
            effinACotton.addErrors(se.getMessage());
        }
        return effinACotton;
    }

    @Override
    /*
    Business Hours
    Monday-Friday: 7:30AM-12:30PM, then 1:00PM-6:00PM
    Saturday: 8:30AM-12:30PM
    Scheduling
    
    It is absolutely essential to prevent Dental Professionals from being double-booked.
     */
    public List<TimeSlot> findOpenAppointments(LocalDate dateOfChoice, Professions title) throws StorageException {

        //redundant check but paranoid. makes sure no appointments are created on a Sunday
        if (dateOfChoice.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new StorageException("We are not open on Sundays. Please pick a new day.");
        }

        List<Appointment> allApptsForDate = findByProfession(dateOfChoice, title);
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

        if (allApptsForDate.isEmpty() || allApptsForDate == null) {
            throw new StorageException("No appointments found on this date.\nPlease either enter an appointment for this date or try another date.");
        }
        //checks for openings at the open time on the weekend
        List<TimeSlot> open = new ArrayList<>();
        if (isWeekend(dateOfChoice)) {
            allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(0), openWE));
            for (int i = 0; i < allApptsForDate.size(); i++) {
                if (i + 1 > allApptsForDate.size() - 1) {
                    open = (findGapEndOfDay(allApptsForDate.get(i), closeWE));
                    if (open != null) {
                        open.forEach((ts) -> {
                            allOpenTimes.add(ts);
                        });
                    } else if (allApptsForDate.get(i).getProfessional().getProfessionalID()
                            != allApptsForDate.get(i + 1).getProfessional().getProfessionalID()) {
                        open = (findGapEndOfDay(allApptsForDate.get(i), closeWE));
                        if (open != null) {
                            open.forEach((ts) -> {
                                allOpenTimes.add(ts);
                            });
                            allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(i + 1), openWE));
                        } else {
                            open = findGap(allApptsForDate.get(i), allApptsForDate.get(i + 1));
                            if (open != null) {
                                open.forEach((ts) -> {
                                    allOpenTimes.add(ts);
                                });
                            }
                        }
                    }
                }
            }
        } //route for appointments during the week
        else {
            allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(0), openWD));
            for (int i = 0; i < allApptsForDate.size(); i++) {
                if (i + 1 > allApptsForDate.size() - 1) {
                    open = (findGapEndOfDay(allApptsForDate.get(i), closeWD));
                    if (open != null) {
                        open.forEach((ts) -> {
                            allOpenTimes.add(ts);
                        });
                    }
                } else if (allApptsForDate.get(i).getProfessional().getProfessionalID()
                        != allApptsForDate.get(i + 1).getProfessional().getProfessionalID()) {
                    open = (findGapEndOfDay(allApptsForDate.get(i), closeWD));
                    if (open != null) {
                        open.forEach((ts) -> {
                            allOpenTimes.add(ts);
                        });
                    }
                    allOpenTimes.add(findGapStartOfDay(allApptsForDate.get(i + 1), openWD));
                } else {
                    open = findGap(allApptsForDate.get(i), allApptsForDate.get(i + 1));
                    if (open != null) {
                        open.forEach((ts) -> {
                            allOpenTimes.add(ts);
                        });
                    }
                }
            }
        }

        List<TimeSlot> cleaned = new ArrayList<>();
        for (int j = 0; j < allOpenTimes.size(); j++) {
            if (allOpenTimes.get(j) != null) {
                cleaned.add(allOpenTimes.get(j));
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
    
    need to adjust for case 4. Need to return two Slots, not just one
    adjust above to .addAll, and return a List from here
     */
    private List<TimeSlot> findGap(Appointment first, Appointment second) {
        List<TimeSlot> slots = new ArrayList<>();
        TimeSlot opening = new TimeSlot();

        //checks first appointment in list that it is at opening time
        if (first.getEndTime().compareTo(second.getStartTime()) < 0) {
            opening.setStartTime(first.getEndTime());
            opening.setEndTime(second.getStartTime());
            opening.setProfessional(first.getProfessional());
        } else {
            return null;
        }
        /*
            1 - the time slot is exaclty lunch
            2 - the appointment starts before lunch, ends at 13:00. Need to set end time to 12:30
            3 - Start time is 12:30, end time goes past 13:00. Adjust start time to 13:00
            4 - Start time is before lunch, end time is after lunch. Need to create two TimeSlots
                Adjust first so End time is 12:30, adjust second so Start Time is 13:00
            0 - Start time and end time are both before 12:30 or after 13:00, meaning no conflict
         */
        switch (conflictsWithLunch(opening)) {
            case 0:
                slots.add(opening);
                return slots;
            case 1:
                return null;
            case 2:
                opening.setEndTime(LocalTime.of(12, 30));
                slots.add(opening);
                return slots;
            case 3:
                opening.setStartTime(LocalTime.of(13, 00));
                slots.add(opening);
                return slots;
            case 4:
                TimeSlot afterLunch = new TimeSlot(opening.getProfessional(), LocalTime.of(13, 00), opening.getEndTime());
                opening.setEndTime(LocalTime.of(12, 30));
                slots.add(opening);
                slots.add(afterLunch);
                return slots;
            default:
                return null;
        }
    }

    /*
    checks for open appointments at the end of the day
    had to reformat this because it wasn't originally checking any times. It would
    simply check the time without taking lunch into account
     */
    private List<TimeSlot> findGapEndOfDay(Appointment appt, LocalTime close) {
        TimeSlot lastButLeast = new TimeSlot(appt.getProfessional(), appt.getStartTime(), close);
        List<TimeSlot> slots = new ArrayList<>();
        switch (conflictsWithLunch(lastButLeast)) {
            case 0:
                slots.add(lastButLeast);
                return slots;
            case 1:
                return null;
            case 2:
                lastButLeast.setEndTime(LocalTime.of(12, 30));
                slots.add(lastButLeast);
                return slots;
            case 3:
                lastButLeast.setStartTime(LocalTime.of(13, 00));
                slots.add(lastButLeast);
                return slots;
            case 4:
                TimeSlot afterLunch = new TimeSlot(lastButLeast.getProfessional(), LocalTime.of(13, 00), lastButLeast.getEndTime());
                lastButLeast.setEndTime(LocalTime.of(12, 30));
                slots.add(lastButLeast);
                slots.add(afterLunch);
                return slots;
            default:
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

    /*
    1 - the time slot is exaclty lunch
    2 - the appointment starts before lunch, ends at 13:00. Need to set end time to 12:30
    3 - Start time is 12:30, end time goes past 13:00. Adjust start time to 13:00
    4 - Start time is before lunch, end time is after lunch. Need to create two TimeSlots
        Adjust first so End time is 12:30, adjust second so Start Time is 13:00
    0 - Start time and end time are both before 12:30 or after 13:00, meaning no conflict
     */
    private static int conflictsWithLunch(TimeSlot opening) {
        LocalTime startLunch = LocalTime.of(12, 30);
        LocalTime endLunch = LocalTime.of(13, 00);

        if ((opening.getStartTime().compareTo(startLunch) < 0
                && opening.getEndTime().compareTo(startLunch) <= 0)
                || (opening.getStartTime().compareTo(endLunch) >= 0
                && opening.getEndTime().compareTo(endLunch) > 0)) {
            return 0;
        } else if (opening.getStartTime().compareTo(startLunch) == 0
                && opening.getEndTime().compareTo(endLunch) == 0) {
            return 1;
        } else if (opening.getStartTime().compareTo(startLunch) < 0
                && opening.getEndTime().compareTo(endLunch) == 0) {
            return 2;
        } else if (opening.getStartTime().compareTo(startLunch) == 0
                && opening.getEndTime().compareTo(endLunch) > 0) {
            return 3;
        } else if (opening.getStartTime().compareTo(startLunch) < 0
                && opening.getEndTime().compareTo(endLunch) > 0) {
            return 4;
        } else {
            return 10;
        }

    }
}
