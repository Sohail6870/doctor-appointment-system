package com.marktine.calendar.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "start_time")
    private LocalTime start;
    @Column(name = "end_time")
    private LocalTime end;

    public Doctor() {}

    public Doctor(Long id, String name, LocalTime start, LocalTime end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }
}
