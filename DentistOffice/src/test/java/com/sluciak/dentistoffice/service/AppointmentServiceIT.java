/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.AppointmentDao;
import com.sluciak.dentistoffice.data.AppointmentFileDaoStub;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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
public class AppointmentServiceIT {

    private final AppointmentDao dao = new AppointmentFileDaoStub();
    private final AppointmentService apptService = new AppointmentService(dao);

    Professional prof = new Professional(1, "Sunshine", "Stebbing", Professions.DENTIST, new BigDecimal("185.00"));
    Professional prof2 = new Professional(2, "Merline", "Lidstone", Professions.HYGIENIST, new BigDecimal("80.00"));
    Professional prof3 = new Professional(3, "Floyd", "Gergolet", Professions.HYGIENIST, new BigDecimal("80.00"));
    Professional prof4 = new Professional(4, "Henryetta", "Bovingdon", Professions.ORTHODONTIST, new BigDecimal("250.00"));
    Professional prof5 = new Professional(5, "Bibby", "Woodfield", Professions.ORAL_SURGEON, new BigDecimal("325.00"));

    Patient pat = new Patient(1, "Kearny", "Estabrook", LocalDate.of(1942, Month.SEPTEMBER, 19));
    Patient pat2 = new Patient(2, "Alejoa", "Lathom", LocalDate.of(1982, Month.AUGUST, 06));
    Patient pat3 = new Patient(3, "Gretchen", "Guiso", LocalDate.of(1929, Month.JUNE, 28));
    Patient pat4 = new Patient(4, "Sigmund", "Lossman", LocalDate.of(2013, Month.JULY, 21));
    Patient pat5 = new Patient(5, "Emily", "Gatecliff", LocalDate.of(1931, Month.JANUARY, 07));
    Patient pat6 = new Patient(6, "Loy", "Adamiec", LocalDate.of(1979, Month.JANUARY, 10));
    Patient pat7 = new Patient(7, "Kerr", "Worman", LocalDate.of(1922, Month.JULY, 28));

    Appointment appt1 = new Appointment(pat, prof, LocalTime.of(8, 30), LocalTime.of(9, 30), new BigDecimal("185.00"), "");
    Appointment appt2 = new Appointment(pat2, prof, LocalTime.of(9, 30), LocalTime.of(10, 30), new BigDecimal("185.00"), "");
    Appointment appt3 = new Appointment(pat3, prof, LocalTime.of(10, 30), LocalTime.of(12, 30), new BigDecimal("370.00"), "");
    Appointment appt4 = new Appointment(pat3, prof3, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"), "needs to start flossing");

    Appointment appt5 = new Appointment(pat3, prof3, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"), "");

    public AppointmentServiceIT() {
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

    /**
     * Test of updateAppointment method, of class AppointmentService.
     */
    @Test
    public void testUpdateAppointment() {
        appt1.setNotes("teeth almost as yellow as corn");
        assertNotNull(apptService.updateAppointment(LocalDate.of(2019, 12, 30), appt1));
    }

    /**
     * Test of cancelAppointment method, of class AppointmentService.
     */
    @Test
    public void testCancelAppointment() {
        ErrorMessage try1 = apptService.cancelAppointment(LocalDate.of(2019, 12, 30), appt5);
        assertTrue(try1.hasError());
    }

    @Test
    public void testAddAppointment() {
        assertNotNull(apptService.addAppointment(LocalDate.of(2019, 12, 30), appt5));
    }

    /**
     * Test of findOpenAppointments method, of class AppointmentService.
     */
    @Test
    public void testFindOpenAppointments() throws Exception {
        assertNotNull(apptService.findOpenAppointments(LocalDate.of(2019, 12, 30), Professions.DENTIST));
        assertFalse(apptService.findOpenAppointments(LocalDate.of(2019, 12, 30), Professions.ORAL_SURGEON).isEmpty());
    }

}
