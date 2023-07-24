package com.example.DoctorApp.repository;

import com.example.DoctorApp.model.Appointment;
import com.example.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepo extends JpaRepository<Appointment,Long> {
    Appointment findFirstByPatient(Patient patient);
}
