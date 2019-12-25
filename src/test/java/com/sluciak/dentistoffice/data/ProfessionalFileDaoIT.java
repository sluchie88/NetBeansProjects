/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
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
public class ProfessionalFileDaoIT {

    ProfessionalFileDao dao = new ProfessionalFileDao("professionals.txt");

    public ProfessionalFileDaoIT() {
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
     * Test of findAll method, of class ProfessionalFileDao.
     */
    @Test
    public void testFindAll() throws Exception {
        assertNotNull(dao.findAll());
    }

    /**
     * Test of findByProfession method, of class ProfessionalFileDao.
     */
    @Test
    public void testFindByProfession() throws Exception {
        assertNotNull(dao.findByProfession(Professions.DENTIST));
        assertFalse(dao.findByProfession(Professions.ORAL_SURGEON).isEmpty());
    }

    /**
     * Test of findByLastName method, of class ProfessionalFileDao.
     */
    @Test
    public void testFindByLastName() throws Exception {
        assertNotNull(dao.findByLastName("McCrann"));
        assertNull(dao.findByLastName("Varhol"));
    }

    /**
     * Test of findById method, of class ProfessionalFileDao.
     */
    @Test
    public void testFindById() {
        try {
            assertNull(dao.findById(25));
            assertNotNull(dao.findById(5));
        }catch(StorageException se){
            System.out.println(se.getMessage());
        }

    }

}
