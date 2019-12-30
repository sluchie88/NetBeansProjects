/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Appointment;
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
    public Appointment addAppointment(LocalDate date, Appointment appt) throws StorageException {
        List<Appointment> allAppts = findByDate(date);
        if (!allAppts.contains(appt)) {
            allAppts.add(appt);
            allAppts.sort((a, b) -> a.getProfessional().getLastName().compareTo(b.getProfessional().getLastName()));
            writeObject(allAppts, this::mapToString);
            return appt;
        } else {
            throw new StorageException("This appointment already exists.");
        }
    }

    public List<Appointment> findByDate(LocalDate date) throws StorageException {
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String dateStr = date.toString().replaceAll("-", "");
        String apptFilePath = "appointments_" + dateStr + ".txt";
        super.setPath(apptFilePath);

        return readObject(this::mapToAppointment).stream().collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByProfessionalAndDate(LocalDate date, String lastName) throws StorageException {
        List<Appointment> forDate = findByDate(date);
        Professional pro = PersonCompleter.getProfessionalByLastName(lastName);
        boolean found = false;
        for (Appointment apt : forDate) {
            if (apt.getProfessional().getProfessionalID() == pro.getProfessionalID()) {
                found = true;
            }
        }
        if (found) {
            return forDate.stream()
                    .filter(a -> a.getProfessional().getLastName().equals(lastName))
                    .collect(Collectors.toList());
        } else {
            throw new StorageException("Professional does not exist.");
        }
    }

    @Override
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException {
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String dateStr = date.toString().replaceAll("-", "");
        String apptFilePath = "appointments_" + dateStr + ".txt";
        super.setPath(apptFilePath);

        return readObject(this::mapToAppointment).stream()
                .filter(p -> p.getProfessional().getSpecialty().equals(job)).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDateAndPatient(LocalDate date, int id) throws StorageException {
        List<Appointment> forDate = findByDate(date);
        boolean found = false;
        for (Appointment apt : forDate) {
            if (apt.getPatient().getPatientID() == id) {
                found = true;
                break;
            }
        }
        if (found) {
            return forDate.stream()
                    .filter(a -> a.getPatient().getPatientID() == id)
                    .collect(Collectors.toList());
        } else {
            throw new StorageException("Patient has no appointment on this day.");
        }
    }

    @Override
    /*
    went out of bounds, unsure why
     */
    public Appointment updateAppointment(LocalDate date, Appointment newInfo) throws StorageException {
        List<Appointment> allAppts = findByDate(date);
        int index = -1;
        //originally used indexof but kept returning -1. something with how the old and new appts were
        //getting set in the view. couldn't find a way to fix that so this was my next best solution
        for (int i = 0; i < allAppts.size(); i++) {
            Appointment curr = allAppts.get(i);
            if (curr.getPatient().getPatientID() == newInfo.getPatient().getPatientID()
                    && curr.getProfessional().getProfessionalID() == newInfo.getProfessional().getProfessionalID()
                    && curr.getStartTime().compareTo(newInfo.getStartTime()) == 0
                    && curr.getEndTime().compareTo(newInfo.getEndTime()) == 0) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            allAppts.remove(index);
            allAppts.add(index, newInfo);
            try {
                writeObject(allAppts, this::mapToString);
            } catch (StorageException se) {
                throw new StorageException("Unable to save changes to database");
            }
        } else {
            throw new StorageException("Unable to locate original appointment. Please try again.");
        }
        return newInfo;
    }

    /*
    if(!Validation.exactAppointmentAlreadyExists(allAppts, appt)){
            all
        }
     */
    @Override
    public boolean cancelAppointment(LocalDate date, Appointment toCancel) throws StorageException {
        List<Appointment> allAppts = findByDate(date);
        int index = -1;
        //originally used indexof but kept returning -1. something with how the old and new appts were
        //getting set in the view. couldn't find a way to fix that so this was my next best solution
        for (int i = 0; i < allAppts.size(); i++) {
            Appointment curr = allAppts.get(i);
            if (curr.getPatient().getPatientID() == toCancel.getPatient().getPatientID()
                    && curr.getProfessional().getProfessionalID() == toCancel.getProfessional().getProfessionalID()
                    && curr.getStartTime().compareTo(toCancel.getStartTime()) == 0
                    && curr.getEndTime().compareTo(toCancel.getEndTime()) == 0) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            allAppts.remove(index);
            try {
                writeObject(allAppts, this::mapToString);
            } catch (StorageException se) {
                throw new StorageException("Unable to save changes to the database.");
            }
        } else {
            throw new StorageException("Unable to locate specified appointment. Please try again later.");
        }
        return allAppts.contains(toCancel);
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
        if (tokens.length > 6) {
            appointment.setNotes(tokens[6]);
        }
        return appointment;
    }

    private String mapToString(Appointment appt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (appt.getNotes() == null || appt.getNotes().equalsIgnoreCase("n/a") || appt.getNotes().isBlank()) {
            return String.format("%s,%s,%s,%s,%s,%s,",
                    appt.getPatient().getPatientID(),
                    appt.getProfessional().getLastName(),
                    appt.getProfessional().getSpecialty().getJobTitle(),
                    appt.getStartTime().format(formatter),
                    appt.getEndTime().format(formatter),
                    appt.getTotalCost());
        } else {
            return String.format("%s,%s,%s,%s,%s,%s,%s",
                    appt.getPatient().getPatientID(),
                    appt.getProfessional().getLastName(),
                    appt.getProfessional().getSpecialty().getJobTitle(),
                    appt.getStartTime().format(formatter),
                    appt.getEndTime().format(formatter),
                    appt.getTotalCost(),
                    appt.getNotes());
        }
    }

}
