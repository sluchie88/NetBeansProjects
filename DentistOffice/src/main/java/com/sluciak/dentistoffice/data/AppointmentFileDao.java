/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.service.OpenAppointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professions;
import com.sluciak.dentistoffice.models.Professional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class AppointmentFileDao extends FileDao<Appointment> implements AppointmentDao {

    public AppointmentFileDao() {
        super("", 7, true);
    }

    
    @Override
    public List<Appointment> findByProfessionalAndDate(LocalDate date, String lastName) throws StorageException{
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String dateStr = date.toString().replaceAll("-", "");
        String apptFilePath = "appointments_" + dateStr + ".txt";
        super.setPath(apptFilePath);
        
        return readObject(this::mapToAppointment).stream()
                .filter(apt -> apt.getProfessional().getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException {
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String apptFilePath = "appointments_" + date.toString() + ".txt";
        super.setPath(apptFilePath);
        
        return readObject(this::mapToAppointment).stream()
                .filter(j -> j.getProfessional().getSpecialty().equals(job))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDateAndPatient(LocalDate date, String lastName) throws StorageException {
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String apptFilePath = "appointments_" + date.toString() + ".txt";
        super.setPath(apptFilePath);
        
        return readObject(this::mapToAppointment).stream()
                .filter(apt -> apt.getPatient().getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment updateAppointment(Appointment change) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OpenAppointment cancelAppointment(Appointment toCancel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    may need to handle cases where patient and professional are not fully formed
    in the appointment class. Only have a patientID, Professional's last name and specialty
    Both are identifiable, but may need to handle adding more info.
    Can use this info to comb through the respective Daos
    
    may need to account for blank notes. may make program explode
     */
    private Appointment mapToAppointment(String[] tokens) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        int id = Integer.parseInt(tokens[0]);
        Patient patient = PersonCompleter.getPatientByID(id);

        String profLastName = tokens[1];
        Professional professional = PersonCompleter.getProfessionalByLastName(profLastName);
        professional.setLastName(tokens[1]);
        professional.setSpecialty(Professions.fromString(tokens[2]));

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setProfessional(professional);
        appointment.setStartTime(LocalTime.parse(tokens[3], formatter));
        appointment.setEndTime(LocalTime.parse(tokens[4], formatter));
        appointment.setTotalCost(new BigDecimal(tokens[5]));
        if(tokens.length > 6){
            appointment.setNotes(tokens[6]);
        }
        return appointment;
    }

    private String mapToString(Appointment appt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                appt.getPatient().getPatientID(),
                appt.getProfessional().getLastName(),
                appt.getProfessional().getSpecialty().getJobTitle(),
                appt.getStartTime().format(formatter),
                appt.getEndTime().format(formatter),
                appt.getNotes());
    }

}
