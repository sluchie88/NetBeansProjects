/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

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
public class AppointmentDaoIT {

    AppointmentFileDao dao = new AppointmentFileDao();

    public AppointmentDaoIT() {
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

    Professional pro1 = new Professional(1, "Berny", "Buzek", Professions.DENTIST, new BigDecimal("175.00"));
    Professional pro2 = new Professional(2, "Reade", "O'Dunneen", Professions.DENTIST, new BigDecimal("175.00"));
    Patient pat1 = new Patient(60, "Carl", "Simeon", LocalDate.of(2006, 03, 17));
    Patient pat2 = new Patient(61, "Ines", "Chadwick", LocalDate.of(1967, 8, 21));

    /**
     * Test of findByProfessionalAndDate method, of class AppointmentDao.
     */
    @Test
    public void testFindByProfessionalAndDate() throws Exception {
        assertNotNull(dao.findByProfessionalAndDate(LocalDate.of(2019, Month.DECEMBER, 30), "Toler"));
        try {
            dao.findByProfessionalAndDate(LocalDate.of(2019, Month.DECEMBER, 28), "Sluciak");
            fail();
        } catch (StorageException se) {
            assertTrue(se.getMessage().equals("No professionals with that last name work here"));
        }
    }

    /**
     * Test of findByProfession method, of class AppointmentDao.
     */
    @Test
    public void testFindByProfession() throws Exception {
        assertTrue(dao.findByProfession(LocalDate.of(2019, Month.DECEMBER, 30), Professions.DENTIST).size() > 1);
        assertFalse(dao.findByProfession(LocalDate.of(2019, Month.DECEMBER, 30), Professions.ORAL_SURGEON).isEmpty());
    }

    /**
     * Test of findByDateAndPatient method, of class AppointmentDao.
     */
    @Test
    public void testFindByDateAndPatient() throws Exception {
        Patient hass = new Patient(586, "Upton", "Hassall", LocalDate.of(1958, Month.JANUARY, 15));
        Patient nice = new Patient(5000, "Bill", "Thorn", LocalDate.of(1972, Month.MAY, 20));
        assertTrue(dao.findByDateAndPatient(LocalDate.of(2019, Month.DECEMBER, 30), hass.getPatientID()).size() == 1);
        try {
            assertNull(dao.findByDateAndPatient(LocalDate.of(2019, Month.DECEMBER, 30), nice.getPatientID()).isEmpty());
            fail();
        } catch (StorageException se) {
            assertTrue(se.getMessage().equals("Patient has no appointment on this day."));
        }
    }

    /**
     * Test of updateAppointment method, of class AppointmentDao.
     */
    @Test
    public void testUpdateAppointment() {
        Appointment apptA = new Appointment(725, "Dearn", Professions.DENTIST, LocalTime.of(11, 15), LocalTime.of(12, 30), new BigDecimal("250.00"));
        Appointment apptB = new Appointment(725, "Dearn", Professions.DENTIST, LocalTime.of(11, 15), LocalTime.of(12, 30), new BigDecimal("275.00"));
        Appointment apptC = new Appointment(1312, "O'Dunneen", Professions.DENTIST, LocalTime.of(8, 30), LocalTime.of(8, 45), new BigDecimal("43.75"), "followup on pre-decay");
        try {
            assertNotNull(dao.updateAppointment(LocalDate.of(2019, Month.DECEMBER, 30), apptB));
            assertNull(dao.updateAppointment(LocalDate.of(2019, Month.DECEMBER, 30), apptB));
        } catch (Exception se) {
            System.out.println("Exception caught for Updating Appointment");
        }
    }

    /**
     * Test of cancelAppointment method, of class AppointmentDao.
     */
    @Test
    public void testCancelAppointment() {
        Appointment apptA = new Appointment(pat1, pro1, LocalTime.of(11, 15), LocalTime.of(12, 30), new BigDecimal("250.00"), "");
        Appointment apptC = new Appointment(pat1, pro1, LocalTime.of(11, 15), LocalTime.of(12, 30), new BigDecimal("43.75"), "check fangs");
        try {
            assertTrue(dao.cancelAppointment(LocalDate.of(2019, Month.DECEMBER, 30), apptA));
            assertFalse(dao.cancelAppointment(LocalDate.of(2019, Month.DECEMBER, 30), apptC));
        } catch (StorageException se) {
            System.out.println("Exception caught for Canceling Appointment");
        }
    }

    @Test
    public void testAddAppointment() {
        Appointment apptA = new Appointment(pat1, pro1, LocalTime.of(11, 15), LocalTime.of(12, 30), new BigDecimal("250.00"), "");
        Appointment apptC = new Appointment(pat1, pro1, LocalTime.of(11, 15), LocalTime.of(12, 30), new BigDecimal("43.75"), "check fangs");
        try {
            assertNotNull(dao.addAppointment(LocalDate.of(2019, Month.DECEMBER, 30), apptA));
            assertNull(dao.addAppointment(LocalDate.of(2019, Month.DECEMBER, 30), apptC));
        } catch (StorageException se) {
            System.out.println("Exception caught for Adding Appointment");
        }
    }
}
