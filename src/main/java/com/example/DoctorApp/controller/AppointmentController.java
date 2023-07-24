package com.example.DoctorApp.controller;

import com.example.DoctorApp.model.Appointment;
import com.example.DoctorApp.model.Doctor;
import com.example.DoctorApp.service.AppointmentService;
import com.example.DoctorApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;


    //get all appointments

    @GetMapping("appointments")
    public List<Appointment> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }
}