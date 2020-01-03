/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
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

    public static boolean isWithinOfficeHours(LocalTime start, LocalTime end, LocalDate date) {
        if (!isOnWeekend(date)) {
            LocalTime earliest = LocalTime.of(7, 30);
            LocalTime latest = LocalTime.of(18, 00);
            return (start.compareTo(earliest) >= 0 && start.compareTo(latest) <= 0) 
                    && (end.compareTo(earliest) >= 0 && end.compareTo(latest) <= 0);
        } else {
            switch (date.getDayOfWeek()) {
                case SUNDAY:
                    return false;
                case SATURDAY: {
                    LocalTime earliest = LocalTime.of(8, 30);
                    LocalTime latest = LocalTime.of(12, 30);
                    return (start.compareTo(earliest) >= 0 && start.compareTo(latest) <= 0) 
                    && (end.compareTo(earliest) >= 0 && end.compareTo(latest) <= 0);
                }
                default: {
                    LocalTime earliest = LocalTime.of(7, 30);
                    LocalTime latest = LocalTime.of(18, 00);
                    return (start.compareTo(earliest) >= 0 && start.compareTo(latest) <= 0) 
                    && (end.compareTo(earliest) >= 0 && end.compareTo(latest) <= 0);
                }
            }
        }
    }

    public static boolean isADoubleBooking(List<Appointment> allAppts, Appointment appt) {
        if (allAppts != null && allAppts.size() > 0) {
            for (Appointment a : allAppts) {
                if (a.getProfessional().getLastName().equals(appt.getProfessional().getLastName())
                        && a.getStartTime().compareTo(appt.getStartTime()) == 0
                        && a.getEndTime().compareTo(appt.getEndTime()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canMakeAnotherAppointment(List<Appointment> allAppts, Appointment appt) {
        Patient pat = appt.getPatient();
        Professional pro = appt.getProfessional();
        for (Appointment a : allAppts) {
            if (pat.getPatientID() == a.getPatient().getPatientID()
                    && a.getProfessional().getSpecialty() == pro.getSpecialty()) {
                return false;
            }
        }
        return true;
    }

    /*
    NEVER allow an Appointment outside of business hours.
    DENTIST: min Appointment is 15 minutes, max 3 hours
    HYGIENIST: min 30 minutes, max 2 hours
    ORTHODONTIST: min 15 minutes, max 1 hour
    ORAL_SURGEON: min 30 minutes, max 8 hours (may schedule over lunch if Appointment > 5 hours)
    Only one Appointment per Customer, per Specialty, per day. (A Customer could see a HYGIENIST 
    in the morning and a DENTIST in the afternoon, but never two DENTISTs in a single day.)
     */
    public static boolean isAnOkayLengthAppointment(Appointment appt, Professions prof) {
        Duration amtTime = Duration.between(appt.getStartTime(), appt.getEndTime());
        long totalMins = amtTime.toMinutes();

        switch (prof) {
            case DENTIST:
                return totalMins >= 15 && totalMins <= 180;
            case HYGIENIST:
                return totalMins >= 30 && totalMins <= 120;
            case ORTHODONTIST:
                return totalMins >= 15 && totalMins <= 60;
            case ORAL_SURGEON:
                return totalMins >= 30 && totalMins <= 480;
            default:
                return false;
        }
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
            } else {
                switch (apptN.getProfessional().getSpecialty()) {
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
            }
        } else if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (null == apptN.getProfessional().getSpecialty()) {
                return (lengthInMinutes >= 30 && lengthInMinutes <= 480)
                        && (apptN.getStartTime().compareTo(LocalTime.of(7, 30)) >= 0
                        && apptN.getEndTime().compareTo(LocalTime.of(18, 00)) <= 0);
            } else {
                switch (apptN.getProfessional().getSpecialty()) {
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
            }
        } else {
            return false;
        }
    }

    public static <T> boolean isEmptyList(List<T> listy) {
        return (listy == null || listy.size() <= 0);
    }

    public static boolean isOnWeekend(LocalDate date) {
        return !(date.getDayOfWeek().equals(DayOfWeek.MONDAY)
                || date.getDayOfWeek().equals(DayOfWeek.TUESDAY)
                || date.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)
                || date.getDayOfWeek().equals(DayOfWeek.THURSDAY)
                || date.getDayOfWeek().equals(DayOfWeek.FRIDAY));
    }
}
