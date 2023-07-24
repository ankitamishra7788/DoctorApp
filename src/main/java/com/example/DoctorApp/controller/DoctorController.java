package com.example.DoctorApp.controller;

import com.example.DoctorApp.model.Doctor;
import com.example.DoctorApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;


    //add doctor
    @PostMapping("doctor")
    public String addDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

    //get all doctors

    @GetMapping("doctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }
}