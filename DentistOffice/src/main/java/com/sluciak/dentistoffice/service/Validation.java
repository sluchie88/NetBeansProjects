/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.models.Professions;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author TomTom
 */
public class Validation {

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

    public static boolean conflictsWithLunch(LocalDateTime start, LocalDateTime end, LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        } else {
            LocalDateTime endLunch = LocalDateTime.of(date, LocalTime.of(13, 00));
            LocalDateTime startLunch = LocalDateTime.of(date, LocalTime.of(12, 30));
            return start.isBefore(endLunch) || end.isAfter(startLunch);
        }
    }
    
    public static boolean isAnOkayLengthAppointment(LocalTime start, LocalTime end, Professions prof){
        boolean itsOkay = false;
        
        //fill in the guttyworks
        
        return itsOkay;
    }

}
