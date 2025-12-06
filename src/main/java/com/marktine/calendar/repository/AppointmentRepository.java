package com.marktine.calendar.repository;

import com.marktine.calendar.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctorId = :doctorId " +
           "AND a.start >= :startOfDay AND a.start < :endOfDay")
    List<Appointment> findByDoctorAndDateRange(
        @Param("doctorId") Long doctorId,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay);
}
