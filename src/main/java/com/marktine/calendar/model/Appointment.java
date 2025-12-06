package com.marktine.calendar.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type")
    private AppointmentType appointmentType;
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Appointment() {}

    public Appointment(Long id, Long doctorId, AppointmentType appointmentType, LocalDateTime start, LocalDateTime endTime) {
        this.id = id;
        this.doctorId = doctorId;
        this.appointmentType = appointmentType;
        this.start = start;
        this.endTime = endTime;
    }

    public Long getId() { return id; }
    public Long getDoctorId() { return doctorId; }
    public AppointmentType getAppointmentType() { return appointmentType; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEndTime() { return endTime; }
}
