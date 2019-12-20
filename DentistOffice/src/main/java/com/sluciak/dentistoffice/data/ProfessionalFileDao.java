/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.models.Professional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class ProfessionalFileDao extends FileDao<Professional> implements ProfessionalDao {

    public ProfessionalFileDao(String path){
        super(path, 5, true);
    }
    
    @Override
    public List<Professional> findAll() throws StorageException{
        return readObject(this::mapToProfessional).stream().collect(Collectors.toList());
    }

    @Override
    public List<Professional> findByProfession(Professions prof)  throws StorageException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Professional> findByLastName(String lastName) throws StorageException{
        return readObject(this::mapToProfessional)
                .stream().filter(p -> p.getLastName()
                        .equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Professional findById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String mapToString(Professional prof) {
        String rate = prof.getHourlyRate().toString();
        return String.format("%s,%s,%s,%s,%s", 
                prof.getProfessionalID(),
                prof.getFirstName(),
                prof.getLastName(),
                prof.getSpecialty().getJobTitle(),
                rate);
    }

    private Professional mapToProfessional(String[] tokens) {
        return new Professional(
                Integer.parseInt(tokens[0]),
                tokens[1],
                tokens[2],
                Professions.fromString(tokens[3]),
                new BigDecimal(tokens[4]));
    }
}
