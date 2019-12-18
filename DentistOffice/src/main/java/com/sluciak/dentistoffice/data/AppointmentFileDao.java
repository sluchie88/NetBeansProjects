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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author TomTom
 */
public class AppointmentFileDao implements AppointmentDao {

    @Override
    public List<Appointment> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> findByDate(LocalDate day) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> findByDateAndPatient(LocalDate day, String lastName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Appointment> findOpenAppointments(LocalDate day) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     */
    private Appointment mapToAppointment(String[] tokens) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        Patient patient = new Patient();
        patient.setPatientID(Integer.parseInt(tokens[0]));

        Professional professional = new Professional();
        professional.setLastName(tokens[1]);
        professional.setSpecialty(Professions.fromString(tokens[2]));

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setProfessional(professional);
        appointment.setStartTime(LocalDateTime.parse(tokens[3], formatter));
        appointment.setEndTime(LocalDateTime.parse(tokens[4], formatter));
        appointment.setTotalCost(new BigDecimal(tokens[5]));
        appointment.setNotes(tokens[6]);
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
