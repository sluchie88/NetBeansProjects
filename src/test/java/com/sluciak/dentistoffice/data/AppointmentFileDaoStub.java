/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sluciak.dentistoffice.data;

import com.sluciak.dentistoffice.models.Appointment;
import com.sluciak.dentistoffice.models.Patient;
import com.sluciak.dentistoffice.models.Professional;
import com.sluciak.dentistoffice.models.Professions;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class AppointmentFileDaoStub implements AppointmentDao {

    private List<Appointment> appts = new ArrayList<>();

    public AppointmentFileDaoStub() {
        appts.add(new Appointment(1, "Stebbing", Professions.DENTIST, LocalTime.of(8, 30), LocalTime.of(9, 30), new BigDecimal("185.00")));
        appts.add(new Appointment(2, "Stebbing", Professions.DENTIST, LocalTime.of(9, 30), LocalTime.of(10, 30), new BigDecimal("185.00")));
        appts.add(new Appointment(3, "Stebbing", Professions.DENTIST, LocalTime.of(10, 30), LocalTime.of(12, 30), new BigDecimal("370.00")));

        appts.add(new Appointment(3, "Gergolet", Professions.HYGENIST, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00")));

        appts.add(new Appointment(4, "Lidstone", Professions.HYGENIST, LocalTime.of(9, 00), LocalTime.of(9, 30), new BigDecimal("40.00")));
        appts.add(new Appointment(2, "Lidstone", Professions.HYGENIST, LocalTime.of(10, 30), LocalTime.of(11, 00), new BigDecimal("40.00")));
        appts.add(new Appointment(6, "Lidstone", Professions.HYGENIST, LocalTime.of(11, 00), LocalTime.of(12, 00), new BigDecimal("80.00")));

        appts.add(new Appointment(5, "Bovingdon", Professions.ORTHODONTIST, LocalTime.of(8, 30), LocalTime.of(12, 00), new BigDecimal("875.00")));

        appts.add(new Appointment(7, "Woodfield", Professions.ORAL_SURGEON, LocalTime.of(9, 00), LocalTime.of(11, 00), new BigDecimal("650.00")));
    }

    @Override
    public Appointment addAppointment(LocalDate date, Appointment appt) throws StorageException {
        List<Appointment> allAppts = appts;
        if (!allAppts.contains(appt)) {
            allAppts.add(appt);
            allAppts.sort((a, b) -> a.getProfessional().getLastName().compareTo(b.getProfessional().getLastName()));
            appts = allAppts;
            return appt;
        } else {
            throw new StorageException("This appointment already exists.");
        }
    }

    /**
     * Test of findByDate method, of class AppointmentFileDao.
     */
    public List<Appointment> findByDate(LocalDate date) {
        try {
            return new ArrayList<>(appts);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Test of findByProfessionalAndDate method, of class AppointmentFileDao.
     */
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

    /**
     * Test of findByProfession method, of class AppointmentFileDao.
     */
    @Override
    public List<Appointment> findByProfession(LocalDate date, Professions job) {
        return appts.stream()
                .filter(p -> p.getProfessional().getSpecialty().getValue() == job.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Test of findByDateAndPatient method, of class AppointmentFileDao.
     */
    @Override
    public List<Appointment> findByDateAndPatient(LocalDate date, int id) throws StorageException {
        List<Appointment> forDate = findByDate(date);
        Patient patty = PersonCompleter.getPatientByID(id);
        boolean found = false;

        for (Appointment apt : forDate) {
            if (apt.getPatient().getPatientID() == patty.getPatientID()) {
                found = true;
                break;
            }
        }
        if (found) {
            return forDate.stream()
                    .filter(a -> a.getPatient().getPatientID() == id)
                    .collect(Collectors.toList());
        } else {
            throw new StorageException("Patient does not exist.");
        }
    }

    /**
     * Test of updateAppointment method, of class AppointmentFileDao.
     */
    @Override
    public Appointment updateAppointment(LocalDate date, Appointment newInfo) throws StorageException {
        List<Appointment> appts = findByDateAndPatient(date, newInfo.getPatient().getPatientID());
        List<Appointment> allAppts;
        if (!appts.isEmpty()) {
            for (int i = 0; i < appts.size(); i++) {
                if (theseAppointmentsAreTheSame(appts.get(i), newInfo)) {
                    appts.remove(i);
                    appts.add(i, newInfo);
                    //need to write the appointments to the file;
                    return addAppointment(date, appts.get(i));
                }
            }
        }
        return null;
    }

    /**
     * Test of cancelAppointment method, of class AppointmentFileDao.
     */
    @Override
    public boolean cancelAppointment(LocalDate date, Appointment toCancel) throws StorageException {

        List<Appointment> allAppts = appts;

        if (allAppts.contains(toCancel)) {
            allAppts.remove(toCancel);
            //not sure how to actually write to the file. Syntax is confusing
            //so apparently pressing buttons until the compiler stops yelling is a valid way
            //of solving problems. who knew
            appts = allAppts;
            return true;
        } else {
            return false;
        }
    }

    private boolean theseAppointmentsAreTheSame(Appointment appt1, Appointment appt2) {
        return appt1.getStartTime().compareTo(appt2.getStartTime()) == 0
                && appt1.getEndTime().compareTo(appt2.getEndTime()) == 0
                && appt1.getPatient().getPatientID() == appt2.getPatient().getPatientID()
                && appt1.getProfessional().getLastName().equals(appt2.getProfessional().getLastName());
    }
}
