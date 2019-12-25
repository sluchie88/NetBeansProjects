/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class ProfessionalFileDaoStub implements ProfessionalDao {

    private final ArrayList<Professional> profs = new ArrayList<>();

    public ProfessionalFileDaoStub() {

        profs.add(new Professional(1, "Sunshine", "Stebbing", Professions.DENTIST, new BigDecimal("185.00")));
        profs.add(new Professional(2, "Merline", "Lidstone", Professions.HYGENIST, new BigDecimal("80.00")));
        profs.add(new Professional(3, "Floyd", "Gergolet", Professions.HYGENIST, new BigDecimal("80.00")));
        profs.add(new Professional(4, "Henryetta", "Bovingdon", Professions.ORTHODONTIST, new BigDecimal("250.00")));
        profs.add(new Professional(5, "Bibby", "Woodfield", Professions.ORAL_SURGEON, new BigDecimal("325.00")));
    }

    @Override
    public List<Professional> findByProfession(Professions prof) throws StorageException {
        return profs.stream()
                .filter(p -> p.getSpecialty().compareTo(prof) == 0)
                .collect(Collectors.toList());
    }

    @Override
    public Professional findByLastName(String lastName) throws StorageException {
        return profs.stream().filter(p -> p.getLastName().equalsIgnoreCase(lastName)).findAny().orElse(null);
    }

    @Override
    public Professional findById(int id) {
        return profs.stream().filter(p -> p.getProfessionalID() == id).findAny().orElse(null);
    }

    @Override
    public List<Professional> findAll() throws StorageException {
        return new ArrayList<>(profs);
    
    }

}
