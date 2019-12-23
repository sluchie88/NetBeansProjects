/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.service;

import com.sluciak.dentistoffice.data.PatientDao;
import com.sluciak.dentistoffice.data.PatientFileDaoStub;
import com.sluciak.dentistoffice.data.ProfessionalDao;
import com.sluciak.dentistoffice.data.ProfessionalFileDaoStub;
import com.sluciak.dentistoffice.models.Patient;
import java.time.LocalDate;
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
public class PersonServiceIT {

    private final ProfessionalDao profDao = new ProfessionalFileDaoStub();
    private final PatientDao patDao = new PatientFileDaoStub();

    PersonService pServ = new PersonService(patDao, profDao);
    
    public PersonServiceIT() {
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
     * Test of addNewPatient method, of class PersonService.
     */
    @Test
    public void testAddNewPatient() {
        try {
            assertNotNull(pServ.addNewPatient(new Patient("Tom", "Bombadil", LocalDate.of(1944, Month.MARCH, 13))));
        } catch (Exception se) {
            fail();
        }
        try{
            pServ.addNewPatient(new Patient("Emily", "Gatecliff", LocalDate.of(1931, Month.JANUARY, 07)));
        }catch(Exception se){
            assertTrue(se.getMessage().equals("Patient already exists."));
        }
    }

    /**
     * Test of professionalExists method, of class PersonService.
     */
    @Test
    public void testProfessionalExists() {
        try{
            assertTrue(pServ.findProfByLastName("Stebbing"));
            assertFalse(pServ.findProfByLastName("Sluciak"));
        } catch (Exception se) {
            fail();
        }
        
    }

    /**
     * Test of findProfByLastName method, of class PersonService.
     */
    @Test
    public void testFindProfByLastName() {
        try{
            assertTrue(pServ.findProfByLastName("Stebbing"));
            assertFalse(pServ.findProfByLastName("Sluciak"));
        } catch (Exception se) {
            fail();
        }
    }

    /**
     * Test of findPatientByLastName method, of class PersonService.
     */
    @Test
    public void testFindPatientByLastName() {
        try{
            assertTrue(pServ.findPatientByLastName("Worman") != null);
            assertNull(pServ.findPatientByLastName("Google"));
        } catch (Exception se) {
            fail();
        }
    }
    
    

}
