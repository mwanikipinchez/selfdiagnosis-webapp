package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Appointment;

import java.util.List;

public interface AppointmentService {
void save(Appointment appointment);
List<Appointment> findAllAppointments();

Appointment searchAppointment(String title);
 void delete(String title);

 List<Appointment> findByPatientEmail(String email);



}
