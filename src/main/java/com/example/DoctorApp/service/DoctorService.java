package com.example.DoctorApp.service;

import com.example.DoctorApp.model.Doctor;
import com.example.DoctorApp.repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    IDoctorRepo iDoctorRepo;
    public String addDoctor(Doctor doctor)
    {
        iDoctorRepo.save(doctor);
        return "doctor added";
    }

    public List<Doctor> getAllDoctors() {
        return iDoctorRepo.findAll();
    }
}