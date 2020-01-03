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
import com.sluciak.dentistoffice.service.Validation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author TomTom
 */
public class AppointmentFileDao implements AppointmentDao {

    public static final String DELIMETER = ",";
    public static final String HEADER = "customer_id,dental_pro_lastname,specialty,start_time,end_time,total_cost,notes";

    public AppointmentFileDao() {
    }

    @Override
    public Appointment addAppointment(LocalDate date, Appointment appt) throws StorageException {
        List<Appointment> allAppts = findByDate(date);
        if (!Validation.isADoubleBooking(allAppts, appt) 
                && Validation.canMakeAnotherAppointment(allAppts, appt)) {
            allAppts.add(appt);
            allAppts.sort((a, b) -> a.getProfessional().getLastName().compareTo(b.getProfessional().getLastName()));
            writeAppointments(allAppts, makeIntoFilePath(date));
            return appt;
        } else {
            throw new StorageException("This appointment already exists.");
        }
    }

    public List<Appointment> findByDate(LocalDate date) throws StorageException {
        String path = makeIntoFilePath(date);
        return readFile(path).stream().collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByProfessionalAndDate(LocalDate date, String lastName) throws StorageException {
        List<Appointment> forDate = findByDate(date);
        Professional pro = PersonCompleter.getProfessionalByLastName(lastName);
        if(pro == null){
            throw new StorageException("No professionals with that last name work here");
        }
        boolean found = false;
        for (Appointment apt : forDate) {
            if (apt.getProfessional().getProfessionalID() == pro.getProfessionalID()) {
                found = true;
            }
        }
        if (found) {
            return forDate.stream()
                    .filter(a -> a.getProfessional().getLastName().contains(lastName))
                    .collect(Collectors.toList());
        } else {
            throw new StorageException("No matching professionals found");
        }
    }

    @Override
    public List<Appointment> findByProfession(LocalDate date, Professions job) throws StorageException {
        List<Appointment> forDate = findByDate(date);

        return forDate.stream()
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
                writeAppointments(allAppts, makeIntoFilePath(date));
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
        boolean found = false;
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
                found = true;
                break;
            }
        }
        if (index >= 0) {
            allAppts.remove(index);
            try {
                writeAppointments(allAppts, makeIntoFilePath(date));
            } catch (StorageException se) {
                throw new StorageException("Unable to save changes to the database.");
            }
        } else {
            throw new StorageException("Unable to locate specified appointment. Please try again later.");
        }
        return !allAppts.contains(toCancel);
    }

    /*
    may need to handle cases where patient and professional are not fully formed
    in the appointment class. Only have a patientID, Professional's last name and specialty
    Both are identifiable, but may need to handle adding more info.
    Can use this info to comb through the respective Daos
    
    may need to account for blank notes. may make program explode
     */
    private Appointment mapToAppointment(String intake) {
        String[] tokens = intake.split(DELIMETER);

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

    /*
    refactor to stop using the fileDao class, and write methods to specifically
    read/write/append for these classes
     */
    private List<Appointment> readFile(String path) throws StorageException {
        List<Appointment> apptList = new ArrayList<>();
        Scanner scanny;
        try {
            File file = new File(path);
            if (!file.exists()) {
                FileWriter writer = new FileWriter(file);
                writer.write("customer_id,dental_pro_lastname,specialty,start_time,end_time,total_cost,notes");
                writer.flush();
                writer.close();
            }
        } catch (IOException ex) {
        }
        try {
            scanny = new Scanner(new BufferedReader(new FileReader(path)));
        } catch (FileNotFoundException fnfe) {
            throw new StorageException("File not found.");
        }
        //should skip the header
        String currLine = scanny.nextLine();
        Appointment currAppt;
        while (scanny.hasNextLine()) {
            currLine = scanny.nextLine();
            currAppt = mapToAppointment(currLine);
            apptList.add(currAppt);
        }
        scanny.close();
        return apptList;
    }

    private void writeAppointments(List<Appointment> listy, String path) throws StorageException {
        try (PrintWriter rowling = new PrintWriter(new FileWriter(path))) {
            rowling.println(HEADER);
            for (Appointment a : listy) {
                rowling.println(mapToString(a));
            }
        } catch (IOException ioe) {
            throw new StorageException("Write to file unsuccessful.");
        }
    }

    private String makeIntoFilePath(LocalDate date) {
        date.format(DateTimeFormatter.ofPattern("yyyyddMM"));
        String dateStr = date.toString().replaceAll("-", "");
        String apptFilePath = "appointments_" + dateStr + ".txt";
        return apptFilePath;
    }
}
