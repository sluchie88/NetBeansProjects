/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Professions;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class Validation<T> {

    public static boolean isNullOrWhiteSpace(String something) {
        return something == null || something.trim().length() == 0;
    }

    public static boolean isWithinOfficeHours(LocalDateTime start, LocalDateTime end, LocalDate date) {
        if (null == date.getDayOfWeek()) {
            LocalDateTime earliest = LocalDateTime.of(date, LocalTime.of(7, 30));
            LocalDateTime latest = LocalDateTime.of(date, LocalTime.of(18, 00));
            return start.isBefore(earliest) || end.isAfter(latest);
        } else {
            switch (date.getDayOfWeek()) {
                case SUNDAY:
                    return false;
                case SATURDAY: {
                    LocalDateTime earliest = LocalDateTime.of(date, LocalTime.of(8, 30));
                    LocalDateTime latest = LocalDateTime.of(date, LocalTime.of(12, 30));
                    return start.isBefore(earliest) || end.isAfter(latest);
                }
                default: {
                    LocalDateTime earliest = LocalDateTime.of(date, LocalTime.of(7, 30));
                    LocalDateTime latest = LocalDateTime.of(date, LocalTime.of(18, 00));
                    return start.isBefore(earliest) || end.isAfter(latest);
                }
            }
        }
    }

    public static boolean isAnOkayLengthAppointment(Appointment appt, Professions prof) {
        boolean itsOkay = false;

        //fill in the guttyworks
        return itsOkay;
    }

    /*
    
     */
    public static boolean exactAppointmentAlreadyExists(List<Appointment> allAppts, Appointment appt) {
        boolean exists = false;
        for (Appointment a : allAppts) {
            if (a.getPatient().getPatientID() == appt.getPatient().getPatientID()
                    && a.getProfessional().getProfessionalID() == appt.getProfessional().getProfessionalID()
                    && a.getStartTime().compareTo(appt.getStartTime()) != 0
                    && a.getEndTime().compareTo(appt.getEndTime()) != 0
                    && a.getTotalCost().equals(appt.getTotalCost())
                    && a.getNotes().equals(appt.getNotes())) {
                exists = true;
            }
        }

        return exists;
    }

    /*
    NEVER allow an Appointment outside of business hours.
    DENTIST: min Appointment is 15 minutes, max 3 hours
    HYGIENIST: min 30 minutes, max 2 hours
    ORTHODONTIST: min 15 minutes, max 1 hour
    ORAL_SURGEON: min 30 minutes, max 8 hours (may schedule over lunch if Appointment > 5 hours)
    Only one Appointment per Customer, per Specialty, per day. (A Customer could see a HYGIENIST 
    in the morning and a DENTIST in the afternoon, but never two DENTISTs in a single day.)
    
    Business Hours
    Monday-Friday: 7:30AM-12:30PM, then 1:00PM-6:00PM
    Saturday: 8:30AM-12:30PM
    Scheduling
    
    It is absolutely essential to prevent Dental Professionals from being double-booked.
    
    
    Created this method to make sure a newly added appointment is valid for both length 
    of appointment and when it starts/ends
     */
    public static boolean appointmentIsOkay(LocalDate date, Appointment apptN) {
        Duration lengthOfAppt = Duration.between(apptN.getStartTime(), apptN.getEndTime());
        long lengthInMinutes = lengthOfAppt.toMinutes();
        
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            if (null == apptN.getProfessional().getSpecialty()) {
                return (lengthInMinutes >= 30 && lengthInMinutes <= 240)
                        && (apptN.getStartTime().compareTo(LocalTime.of(8, 30)) >= 0
                        && apptN.getEndTime().compareTo(LocalTime.of(12, 30)) <= 0);
            } else switch (apptN.getProfessional().getSpecialty()) {
                case DENTIST:
                    return (lengthInMinutes >= 15 && lengthInMinutes <= 180)
                            && (apptN.getStartTime().compareTo(LocalTime.of(8, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(12, 30)) <= 0);
                case HYGIENIST:
                    return (lengthInMinutes >= 30 && lengthInMinutes <= 120)
                            && (apptN.getStartTime().compareTo(LocalTime.of(8, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(12, 30)) <= 0);
                case ORTHODONTIST:
                    return (lengthInMinutes >= 15 && lengthInMinutes <= 60)
                            && (apptN.getStartTime().compareTo(LocalTime.of(8, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(12, 30)) <= 0);
                default:
                    return (lengthInMinutes >= 30 && lengthInMinutes <= 240)
                            && (apptN.getStartTime().compareTo(LocalTime.of(8, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(12, 30)) <= 0);
            }
        } else if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (null == apptN.getProfessional().getSpecialty()) {
                return (lengthInMinutes >= 30 && lengthInMinutes <= 480)
                        && (apptN.getStartTime().compareTo(LocalTime.of(7, 30)) >= 0
                        && apptN.getEndTime().compareTo(LocalTime.of(18, 00)) <= 0);
            } else switch (apptN.getProfessional().getSpecialty()) {
                case DENTIST:
                    return (lengthInMinutes >= 15 && lengthInMinutes <= 180)
                            && (apptN.getStartTime().compareTo(LocalTime.of(7, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(18, 00)) <= 0);
                case HYGIENIST:
                    return (lengthInMinutes >= 30 && lengthInMinutes <= 120)
                            && (apptN.getStartTime().compareTo(LocalTime.of(7, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(18, 00)) <= 0);
                case ORTHODONTIST:
                    return (lengthInMinutes >= 15 && lengthInMinutes <= 60)
                            && (apptN.getStartTime().compareTo(LocalTime.of(7, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(18, 00)) <= 0);
                default:
                    return (lengthInMinutes >= 30 && lengthInMinutes <= 480)
                            && (apptN.getStartTime().compareTo(LocalTime.of(7, 30)) >= 0
                            && apptN.getEndTime().compareTo(LocalTime.of(18, 00)) <= 0);
            }
        } else {
            return false;
        }
    }

    static <T> boolean isEmptyList(List<T> listy) {
        return (listy == null || listy.size() <= 0);
    }
}