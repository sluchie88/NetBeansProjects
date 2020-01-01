/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    private final Validation validate = new Validation();
    Professional prof = new Professional(1, "Sunshine", "Stebbing", Professions.DENTIST, new BigDecimal("185.00"));
    Appointment appt1 = new Appointment(1, "Buzek", Professions.DENTIST, LocalTime.of(8, 30), LocalTime.of(9, 30), new BigDecimal("185.00"));
    Appointment appt2 = new Appointment(2, "Buzek", Professions.DENTIST, LocalTime.of(7, 30), LocalTime.of(16, 00), new BigDecimal("185.00"));
    List<Appointment> allAppts = new ArrayList<>();

    /**
     * Test of isAnOkayLengthAppointment method, of class Validation.
     */
    @Test
    public void testIsAnOkayLengthAppointment() {
        assertTrue(validate.isAnOkayLengthAppointment(new Appointment(1, "Buzek", Professions.DENTIST, LocalTime.of(8, 30), LocalTime.of(9, 00), new BigDecimal("185.00")), Professions.DENTIST));
        assertFalse(validate.isAnOkayLengthAppointment(new Appointment(2, "Buzek", Professions.DENTIST, LocalTime.of(07, 30), LocalTime.of(16, 00), new BigDecimal("185.00")), Professions.DENTIST));
    }

    /**
     * Test of isEmptyList method, of class Validation.
     */
    @Test
    public void testIsEmptyList() {
        allAppts.add(appt1);
        assertFalse(validate.isEmptyList(allAppts));
        List<Appointment> boogie = new ArrayList<>();
        assertTrue(validate.isEmptyList(boogie));        
    }

    /**
     * Test of isOnWeekend method, of class Validation.
     */
    @Test
    public void testIsOnWeekend() {
        assertTrue(validate.isOnWeekend(LocalDate.of(2020, Month.JANUARY, 19)));
        assertFalse(validate.isOnWeekend(LocalDate.of(2020, Month.JANUARY, 20)));
    }
    
}
