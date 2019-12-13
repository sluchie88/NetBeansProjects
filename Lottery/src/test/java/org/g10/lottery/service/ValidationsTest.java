/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.g10.lottery.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author cmarch
 */
public class ValidationsTest {
    
    public ValidationsTest() {
    }

    @Test
    public void testInRange() {
    }

    @Test
    public void testIsNullOrWhitespace() {
    }

    @Test
    public void testIsEmail() {
        boolean isEmail = Validations.isEmail("nope");
        assertTrue(isEmail);
    }
    
}
