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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author TomTom
 */
public class ValidationIT {

    public ValidationIT() {

    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    Professional prof = new Professional(1, "Sunshine", "Stebbing", Professions.DENTIST, new BigDecimal("185.00"));
    Professional prof2 = new Professional(2, "Merline", "Lidstone", Professions.HYGIENIST, new BigDecimal("80.00"));
    Patient pat1 = new Patient(1, "Kearny", "Estabrook", LocalDate.of(1942, Month.SEPTEMBER, 19));
    Patient pat2 = new Patient(2, "Alejoa", "Lathom", LocalDate.of(1982, Month.AUGUST, 06));

    Appointment appt1 = new Appointment(pat1, prof, LocalTime.of(8, 30), LocalTime.of(9, 30), new BigDecimal("185.00"), "");
    Appointment appt2 = new Appointment(pat1, prof2, LocalTime.of(7, 30), LocalTime.of(16, 00), new BigDecimal("185.00"), "");
    Appointment appt3 = new Appointment(pat2, prof2, LocalTime.of(11, 30), LocalTime.of(12, 30), new BigDecimal("80.00"), "");
    List<Appointment> allAppts = new ArrayList<>();

    /**
     * Test of isAnOkayLengthAppointment method, of class Validation.
     */
    @Test
    public void testIsAnOkayLengthAppointment() {
        assertTrue(Validation.isAnOkayLengthAppointment(new Appointment(1, "Buzek", Professions.DENTIST, LocalTime.of(8, 30), LocalTime.of(9, 00), new BigDecimal("185.00")), Professions.DENTIST));
        assertFalse(Validation.isAnOkayLengthAppointment(new Appointment(2, "Buzek", Professions.DENTIST, LocalTime.of(07, 30), LocalTime.of(16, 00), new BigDecimal("185.00")), Professions.DENTIST));
    }

    /**
     * Test of isEmptyList method, of class Validation.
     */
    @Test
    public void testIsEmptyList() {
        allAppts.add(appt1);
        assertFalse(Validation.isEmptyList(allAppts));
        List<Appointment> boogie = new ArrayList<>();
        assertTrue(Validation.isEmptyList(boogie));
    }

    /**
     * Test of isOnWeekend method, of class Validation.
     */
    @Test
    public void testIsOnWeekend() {
        assertTrue(Validation.isOnWeekend(LocalDate.of(2020, Month.JANUARY, 19)));
        assertFalse(Validation.isOnWeekend(LocalDate.of(2020, Month.JANUARY, 20)));
    }

    @Test
    public void testCanMakeAnotherAppointment() {
        allAppts.add(appt3);
        assertTrue(Validation.canMakeAnotherAppointment(allAppts, appt2));
        assertFalse(Validation.canMakeAnotherAppointment(allAppts, appt3));
    }

    @Test
    public void testAppointmentIsOkay() {
        assertTrue(Validation.appointmentIsOkay(LocalDate.of(2020, Month.MARCH, 03), appt1));
        assertFalse(Validation.appointmentIsOkay(LocalDate.of(2020, Month.MARCH, 03), appt2));
    }

    @Test
    public void testIsADoubleBooking() {
        allAppts.remove(appt1);
        assertTrue(Validation.isADoubleBooking(allAppts, appt3));
        assertFalse(Validation.isADoubleBooking(allAppts, appt1));
    }

    @Test
    public void testIsWithinOfficeHours() {
        assertFalse(Validation.isWithinOfficeHours(LocalTime.of(18, 15), LocalTime.of(18, 45), LocalDate.of(2020, Month.MARCH, 2)));
        assertTrue(Validation.isWithinOfficeHours(LocalTime.of(8, 15), LocalTime.of(8, 45), LocalDate.of(2020, Month.MARCH, 2)));
    }

    @Test
    public void testIsNullOrWhiteSpace() {
        assertFalse(Validation.isNullOrWhiteSpace("My name is Tom."));
        assertTrue(Validation.isNullOrWhiteSpace(" "));
    }
}
