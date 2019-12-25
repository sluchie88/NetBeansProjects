/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Patient;
import java.time.LocalDate;
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
public class PatientFileDaoIT {

    private PatientFileDao dao = new PatientFileDao("patients.txt");

    public PatientFileDaoIT() {
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
     * Test of findAll method, of class PatientFileDao.
     */
    @Test
    public void testFindAll() throws Exception {
        assertTrue(dao.findAll().size() >= 1000);
        assertFalse(dao.findAll().isEmpty());
    }

    /**
     * Test of findByBirthdate method, of class PatientFileDao.
     */
    @Test
    public void testFindByBirthdate() throws Exception {
        try {
            dao.findByBirthdate(LocalDate.of(2022, 03, 05));
        } catch (StorageException se) {
            assertTrue(se.getMessage().equals("No patients have that birthday"));
        }
        assertNotNull(dao.findByBirthdate(LocalDate.of(1983, 04, 06)));
    }

    /**
     * Test of findByLastName method, of class PatientFileDao.
     */
    @Test
    public void testFindByLastName() throws Exception {
        assertNull(dao.findByLastName("Dukington"));
        assertNotNull(dao.findByLastName("Woodyear"));
    }

    /**
     * Test of add method, of class PatientFileDao.
     */
    @Test
    public void testAdd() throws Exception {
        Patient existing = new Patient("Neile", "Rowbottom", LocalDate.of(2007, 9, 5));

        assertNull(dao.add(existing));
    }

    /**
     * Test of findByID method, of class PatientFileDao.
     */
    @Test
    public void testFindByID() throws Exception {
        assertNull(dao.findByID(5000));
        assertNotNull(dao.findByID(222));
    }

}
