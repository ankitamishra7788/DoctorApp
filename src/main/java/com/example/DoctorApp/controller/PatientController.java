package com.example.DoctorApp.controller;

import com.example.DoctorApp.model.Appointment;
import com.example.DoctorApp.model.Doctor;
import com.example.DoctorApp.model.Patient;
import com.example.DoctorApp.model.dto.SignInInput;
import com.example.DoctorApp.model.dto.SignUpOutput;
import com.example.DoctorApp.service.AuthenticationService;
import com.example.DoctorApp.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    AuthenticationService authenticationService;

    //add patient
    @PostMapping("patient/signUp")
    public SignUpOutput signUpPatient(@RequestBody Patient patient){
        return patientService.signUpPatient(patient);
    }

    @PostMapping("patient/signIn")
    public String signInPatient(@RequestBody @Valid SignInInput signInInput){
        return patientService.signInPatient(signInInput);
    }

    //sign out
    @DeleteMapping("patient/signOut")
    public String signOutPatient(String email,String token){
        if(authenticationService.authenticate(email,token)) {
            return patientService.signOutPatient(email);
        }
        else{
            return "sign out not allowed for non-authenticate user";
        }
    }

    //get all patients
    @GetMapping("patients")
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }



    //schedule appointment
    @PostMapping("appointment/schedule")
    public String scheduleAppointment(@RequestBody Appointment appointment,String email,String authToken){

        if(authenticationService.authenticate(email,authToken)){
            boolean status = patientService.scheduleAppointment(appointment);
            return status ? "appointment scheduled":"error occurred during scheduling appointment";
        }
        else{
            return "appointment failed because of invalid authentication";
        }
    }


    //cancel appointment
    @DeleteMapping ("appointment/cancel")
    public String cancelAppointment(String email,String authToken){

        if(authenticationService.authenticate(email,authToken)){
            patientService.cancelAppointment(email);
            return "canceled appointment";
        }
        else{
            return "appointment failed because of invalid authentication";
        }

    }

}