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
    public List<Appointment> findByDateAndPatient(LocalDate date, Patient pat) throws StorageException {
        List<Appointment> forDate = findByDate(date);
        Patient patty = PersonCompleter.getPatientByID(pat.getPatientID());
        boolean found = false;

        for (Appointment apt : forDate) {
            if (apt.getPatient().getPatientID() == patty.getPatientID()) {
                found = true;
                break;
            }
        }
        if (found) {
            return forDate.stream()
                    .filter(a -> a.getPatient().getPatientID() == pat.getPatientID())
                    .collect(Collectors.toList());
        } else {
            throw new StorageException("Patient does not exist.");
        }
    }

    @Override
    public Appointment updateAppointment(LocalDate date, Appointment old, Appointment newInfo) throws StorageException, Exception {
        List<Appointment> appts = findByDateAndPatient(date, old.getPatient());
        List<Appointment> allAppts;
        if (!appts.isEmpty()) {
            for (int i = 0; i < appts.size(); i++) {
                if (theseAppointmentsAreTheSame(appts.get(i), old)) {
                    appts.remove(i);
                    appts.add(i, newInfo);
                    //need to write the appointments to the file;
                    return addAppointment(date, appts.get(i));
                }
            }
        }
        return null;
    }

    @Override
    public boolean cancelAppointment(LocalDate date, Appointment toCancel) throws StorageException {
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String dateStr = date.toString().replaceAll("-", "");
        String apptFilePath = "appointments_" + dateStr + ".txt";
        super.setPath(apptFilePath);

        List<Appointment> appts;
        try {
            appts = findByDate(date);
        } catch (StorageException ex) {
            return false;
        }
        if (appts.contains(toCancel)) {
            appts.remove(toCancel);
            //not sure how to actually write to the file. Syntax is confusing
            //so apparently pressing buttons until the compiler stops yelling is a valid way
            //of solving problems. who knew
            writeObject(appts, (appt) -> this.mapToString(appt));
            return true;
        } else {
            return false;
        }
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
        if (appt.getNotes().equalsIgnoreCase("n/a") || appt.getNotes().isBlank()) {
            return String.format("%s,%s,%s,%s,%s",
                    appt.getPatient().getPatientID(),
                    appt.getProfessional().getLastName(),
                    appt.getProfessional().getSpecialty().getJobTitle(),
                    appt.getStartTime().format(formatter),
                    appt.getEndTime().format(formatter));
        } else {
            return String.format("%s,%s,%s,%s,%s,%s",
                    appt.getPatient().getPatientID(),
                    appt.getProfessional().getLastName(),
                    appt.getProfessional().getSpecialty().getJobTitle(),
                    appt.getStartTime().format(formatter),
                    appt.getEndTime().format(formatter),
                    appt.getNotes());
        }
    }
    //returns true if values the same. returns false if appointments are different

    private boolean theseAppointmentsAreTheSame(Appointment appt1, Appointment appt2) {
        return appt1.getStartTime().compareTo(appt2.getStartTime()) == 0
                && appt1.getEndTime().compareTo(appt2.getEndTime()) == 0
                && appt1.getPatient().getPatientID() == appt2.getPatient().getPatientID()
                && appt1.getProfessional().getLastName().equals(appt2.getProfessional().getLastName());
    }

}
