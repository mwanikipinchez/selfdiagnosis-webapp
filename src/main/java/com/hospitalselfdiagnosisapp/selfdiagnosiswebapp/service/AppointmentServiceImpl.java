package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Appointment;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public void save(Appointment appointment) {

          appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment searchAppointment(String title) {
        return (Appointment) appointmentRepository.findAppointmentByTitle(title);
    }

    @Override
    public void delete(String title) {
        Appointment appointment = (Appointment) appointmentRepository.findAppointmentByTitle(title);
        appointmentRepository.deleteById(appointment.getId());

    }

    @Override
    public List<Appointment> findByPatientEmail(String email) {

        return appointmentRepository.findByPatientEmail(email);
    }
}
