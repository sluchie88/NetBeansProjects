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
import java.time.Month;
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
        Professional prof = new Professional(1, "Sunshine", "Stebbing", Professions.DENTIST, new BigDecimal("185.00"));
        Professional prof2 = new Professional(2, "Merline", "Lidstone", Professions.HYGIENIST, new BigDecimal("80.00"));
        Professional prof3 = new Professional(3, "Floyd", "Gergolet", Professions.HYGIENIST, new BigDecimal("80.00"));
        Professional prof4 = new Professional(4, "Henryetta", "Bovingdon", Professions.ORTHODONTIST, new BigDecimal("250.00"));
        Professional prof5 = new Professional(5, "Bibby", "Woodfield", Professions.ORAL_SURGEON, new BigDecimal("325.00"));
        
        Patient pat = new Patient(1, "Kearny", "Estabrook", LocalDate.of(1942, Month.SEPTEMBER, 19));
        Patient pat2 = new Patient(2, "Alejoa", "Lathom", LocalDate.of(1982, Month.AUGUST, 06));
        Patient pat3 = new Patient(3, "Gretchen", "Guiso", LocalDate.of(1929, Month.JUNE, 28));
        Patient pat4 = new Patient(4, "Sigmund", "Lossman", LocalDate.of(2013, Month.JULY, 21));
        Patient pat5 = new Patient(5, "Emily", "Gatecliff", LocalDate.of(1931, Month.JANUARY, 07));
        Patient pat6 = new Patient(6, "Loy", "Adamiec", LocalDate.of(1979, Month.JANUARY, 10));
        Patient pat7 = new Patient(7, "Kerr", "Worman", LocalDate.of(1922, Month.JULY, 28));
        
        
        appts.add(new Appointment(pat, prof, LocalTime.of(8, 30), LocalTime.of(9, 30), new BigDecimal("185.00"), ""));
        appts.add(new Appointment(pat2, prof, LocalTime.of(9, 30), LocalTime.of(10, 30), new BigDecimal("185.00"), ""));
        appts.add(new Appointment(pat3, prof, LocalTime.of(10, 30), LocalTime.of(12, 30), new BigDecimal("370.00"), ""));
        appts.add(new Appointment(pat3, prof3, LocalTime.of(10, 00), LocalTime.of(10, 30), new BigDecimal("80.00"), ""));
        appts.add(new Appointment(pat4, prof2, LocalTime.of(9, 00), LocalTime.of(9, 30), new BigDecimal("40.00"), ""));
        appts.add(new Appointment(pat2, prof2, LocalTime.of(10, 30), LocalTime.of(11, 00), new BigDecimal("40.00"), ""));
        appts.add(new Appointment(pat6, prof2, LocalTime.of(11, 00), LocalTime.of(12, 00), new BigDecimal("80.00"), ""));
        appts.add(new Appointment(pat5, prof4, LocalTime.of(8, 30), LocalTime.of(12, 00), new BigDecimal("875.00"), ""));
        appts.add(new Appointment(pat7, prof5, LocalTime.of(9, 00), LocalTime.of(11, 00), new BigDecimal("650.00"), ""));
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
        List<Appointment> listy = new ArrayList<>();
        for(Appointment a : appts){
            if(a.getProfessional().getSpecialty() == job){
                listy.add(a);
            }
        }
        return listy;
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
