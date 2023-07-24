package com.example.DoctorApp.service;

import com.example.DoctorApp.model.Appointment;
import com.example.DoctorApp.model.AuthenticationToken;
import com.example.DoctorApp.model.Patient;
import com.example.DoctorApp.model.dto.SignInInput;
import com.example.DoctorApp.model.dto.SignUpOutput;
import com.example.DoctorApp.repository.IAppointmentRepo;
import com.example.DoctorApp.repository.IAuthTokenRepo;
import com.example.DoctorApp.repository.IDoctorRepo;
import com.example.DoctorApp.repository.IPatientRepo;
import com.example.DoctorApp.service.utility.EmailHandler;
import com.example.DoctorApp.service.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo iPatientRepo;

    @Autowired
    IAuthTokenRepo iAuthTokenRepo;

    @Autowired
    IDoctorRepo iDoctorRepo;

    @Autowired
    IAppointmentRepo iAppointmentRepo;

    @Autowired
    AppointmentService appointmentService;
    public SignUpOutput signUpPatient(Patient patient) {

        boolean signUpStatus=true;
        String signUpStatusMessage=null;

        String newEmail=patient.getPatientEmail();
        if(newEmail==null){
            signUpStatusMessage="inValid email";
            signUpStatus=false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        //check if this patient email is already exist or not??
        Patient existingpatient=iPatientRepo.findFirstByPatientEmail(newEmail);


        if(existingpatient!=null){
            signUpStatusMessage="Email is already exist";
            signUpStatus=false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        try {
            //hash the password : encrypt the password
            String encryptedPassword = PasswordEncrypter.encryptPassword(patient.getPatientPassword());

            patient.setPatientPassword(encryptedPassword);
            iPatientRepo.save(patient);
            signUpStatusMessage = "patient registered successfully!!!";

            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
        catch(Exception e){
            signUpStatusMessage="Internal error occurred during signup";
            signUpStatus=false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public List<Patient> getAllPatients() {
        return iPatientRepo.findAll();
    }

    public String signInPatient(SignInInput signInInput) {


        String signInStatusMessage=null;

        String signInEmail=signInInput.getEmail();
        if(signInInput==null){

            signInStatusMessage="inValid email";
            return signInStatusMessage;
        }

        //check if this email already exist??

        Patient existingPatient= iPatientRepo.findFirstByPatientEmail(signInEmail);

        if(existingPatient==null){

            signInStatusMessage="email not registered";
            return signInStatusMessage;
        }

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingPatient.getPatientPassword().equals(encryptedPassword)){
                //session should be created since password matched and email id is valid

                AuthenticationToken authenticationToken=new AuthenticationToken(existingPatient);
                iAuthTokenRepo.save(authenticationToken);

                EmailHandler.sendEmail(signInEmail,"testing otp",authenticationToken.getTokenValue());
                return "Token sent to your email";

            }
            else{
                signInStatusMessage="invalid user name or password";
                return signInStatusMessage;
            }
        }
        catch(Exception e){
            signInStatusMessage="internal error occurred during sign in";
            return  signInStatusMessage;
        }
    }


    //schedule the appointment
    public boolean scheduleAppointment(Appointment appointment) {
        //id of doctor
        Long doctorId = appointment.getDoctor().getDoctorId();
        boolean isDoctorValid = iDoctorRepo.existsById(doctorId);

        //id of patient
        Long patientId = appointment.getPatient().getPatientId();
        boolean isPatientValid = iPatientRepo.existsById(patientId);

        if(isDoctorValid && isPatientValid)
        {
            appointmentService.saveAppointment(appointment);
            return true;
        }
        else {
            return false;
        }
    }


    public void cancelAppointment(String email) {
        //email-> patient

        Patient patient=iPatientRepo.findFirstByPatientEmail(email);
        Appointment appointment= appointmentService.getAppointmentForPatient(patient);

        appointmentService.cancelAppointment(appointment);
    }

    public String signOutPatient(String email) {

        Patient patient=iPatientRepo.findFirstByPatientEmail(email);

        iAuthTokenRepo.delete(iAuthTokenRepo.findFirstByPatient(patient));

        return "signed out successfully";
    }
}