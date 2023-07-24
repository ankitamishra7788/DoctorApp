package com.example.DoctorApp.repository;

import com.example.DoctorApp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;

@Repository
public interface IDoctorRepo extends JpaRepository<Doctor,Long> {
}