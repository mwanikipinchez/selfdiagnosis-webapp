package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages, Long> {


}
