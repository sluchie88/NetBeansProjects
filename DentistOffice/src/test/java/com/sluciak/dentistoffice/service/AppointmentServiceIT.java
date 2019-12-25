/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.AppointmentDao;
import com.sluciak.dentistoffice.data.AppointmentFileDaoStub;
import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Professions;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
     * Test of findByDrAndDate method, of class AppointmentService.
     */
    @Test
    public void testFindByDrAndDate() throws Exception {
        assertTrue((apptService.findByDrAndDate(LocalDate.of(2019, 12, 30), "Sunshine")).isEmpty());
        assertTrue((apptService.findByDrAndDate(LocalDate.of(2019, 12, 30), "Lidstone")).size() == 3);
    }

    /**
     * Test of findByProfession method, of class AppointmentService.
     */
    @Test
    public void testFindByProfession() throws Exception {
        assertFalse((apptService.findByProfession(LocalDate.of(2019, 12, 30), Professions.ORAL_SURGEON)).size() > 1);
        assertTrue((apptService.findByProfession(LocalDate.of(2019, 12, 30), Professions.HYGENIST)).size() == 4);
    }

    /**
     * Test of findByDateAndPatient method, of class AppointmentService.
     */
    @Test
    public void testFindByDateAndPatient() throws Exception {
        assertNull(apptService.findByDateAndPatient(LocalDate.of(2019, 12, 30), 26));
        assertNotNull(apptService.findByDateAndPatient(LocalDate.of(2019, 12, 30), 6));
    }

    /**
     * Test of updateAppointment method, of class AppointmentService.
     */
    @Test
    public void testUpdateAppointment() {
        Appointment appt1 = new Appointment(3, "Gergolet", Professions.HYGENIST, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"));
        Appointment appt2 = new Appointment(3, "Gergolet", Professions.HYGENIST, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"), "needs to start flossing");

        assertNull(apptService.updateAppointment(LocalDate.of(2019, 12, 30), appt1));
        assertNotNull(apptService.updateAppointment(LocalDate.of(2019, 12, 30), appt2));
    }

    /**
     * Test of cancelAppointment method, of class AppointmentService.
     */
    @Test
    public void testCancelAppointment() {
        Appointment appt2 = new Appointment(3, "Gergolet", Professions.HYGENIST, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"), "needs to start flossing");

        assertTrue(apptService.cancelAppointment(LocalDate.of(2019, 12, 30), appt2));
        assertFalse(apptService.cancelAppointment(LocalDate.of(2019, 12, 30), appt2));

        apptService.addAppointment(LocalDate.of(2019, 12, 30), appt2);
    }

    @Test
    public void testAddAppointment() {
        Appointment appt2 = new Appointment(3, "Gergolet", Professions.HYGENIST, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"), "needs to start flossing");
        Appointment apptN = new Appointment(7, "Lidstone", Professions.HYGENIST, LocalTime.of(8, 30), LocalTime.of(9, 00), new BigDecimal("80.00"), "needs to start flossing");

        assertNull(apptService.addAppointment(LocalDate.of(2019, 12, 30), appt2));
        assertNotNull(apptService.addAppointment(LocalDate.of(2019, 12, 30), apptN));
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
