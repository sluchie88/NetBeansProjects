/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Professional;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface ProfessionalDao {
    public List<Professional> findAll();
    
    public List<Professional> findByProfession();
    
    public List<Professional> findByLastName();
    
    public Professional findById();
}
