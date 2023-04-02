package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(String user);
    Todo findByActivity(String activity);
}
