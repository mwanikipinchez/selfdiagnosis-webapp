package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="todolist")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String user;
    @Column(nullable = false)
    private String activity;
    @Column(nullable = false)
    private LocalTime time;


}
