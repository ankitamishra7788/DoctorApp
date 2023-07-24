package com.example.DoctorApp.service;

import com.example.DoctorApp.model.Appointment;
import com.example.DoctorApp.model.Patient;
import com.example.DoctorApp.repository.IAppointmentRepo;
import com.example.DoctorApp.repository.IDoctorRepo;
import com.example.DoctorApp.repository.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo iAppointmentRepo;

    @Autowired
    IDoctorRepo iDoctorRepo;

    @Autowired
    IPatientRepo iPatientRepo;




    public List<Appointment> getAllAppointments() {
        return iAppointmentRepo.findAll();
    }

    public void saveAppointment(Appointment appointment) {
        appointment.setAppointmentCreationTime(LocalDateTime.now());
        iAppointmentRepo.save(appointment);
    }

    public Appointment getAppointmentForPatient(Patient patient) {

        return iAppointmentRepo.findFirstByPatient(patient);
    }

    public void cancelAppointment(Appointment appointment) {
        iAppointmentRepo.delete(appointment);
    }
}