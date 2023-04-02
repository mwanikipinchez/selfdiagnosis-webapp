package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientEmail(String email);
    List<Appointment> findByDoctorEmail(String email);
    List<Appointment> findAppointmentByTitle(String title);
}
