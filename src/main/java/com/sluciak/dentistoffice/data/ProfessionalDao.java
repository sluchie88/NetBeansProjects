/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import java.util.List;

/**
 *
 * @author TomTom
 */
public interface ProfessionalDao {
    public List<Professional> findAll()  throws StorageException;
    
    public List<Professional> findByProfession(Professions prof) throws StorageException;
    
    public Professional findByLastName(String lastName) throws StorageException;
    
    public Professional findById(int id) throws StorageException;
}
