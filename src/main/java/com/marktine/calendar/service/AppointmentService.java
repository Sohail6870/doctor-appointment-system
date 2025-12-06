package com.marktine.calendar.service;

import com.marktine.calendar.dto.CreateAppointmentRequest;
import com.marktine.calendar.dto.TimeSlotDto;
import com.marktine.calendar.model.*;
import com.marktine.calendar.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final DoctorRepository doctorRepo;
    private final AppointmentRepository apptRepo;

    public AppointmentService(DoctorRepository doctorRepo, AppointmentRepository apptRepo) {
        this.doctorRepo = doctorRepo;
        this.apptRepo = apptRepo;
    }

    public List<TimeSlotDto> availableSlots(Long doctorId, LocalDate date, AppointmentType type) {
        Doctor d = doctorRepo.findById(doctorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found with id: " + doctorId));

        // use a safe date-time range query
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        List<Appointment> existing = apptRepo.findByDoctorAndDateRange(doctorId, startOfDay, endOfDay);

        List<TimeSlotDto> slots = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(date, d.getStart());
        LocalDateTime end = LocalDateTime.of(date, d.getEnd());

        long duration = type.getDurationMinutes();
        for (LocalDateTime slotStart = start; !slotStart.plusMinutes(duration).isAfter(end); slotStart = slotStart.plusMinutes(duration)) {
            LocalDateTime slotEnd = slotStart.plusMinutes(duration);
            boolean clash = false;
            for (Appointment a : existing) {
                // overlap check (same logic you already had, robust)
                if (slotStart.isBefore(a.getEndTime()) && slotEnd.isAfter(a.getStart())) {
                    clash = true;
                    break;
                }
            }
            if (!clash) slots.add(new TimeSlotDto(slotStart, slotEnd));
        }
        return slots;
    }

    public Appointment book(CreateAppointmentRequest req) {
        Doctor d = doctorRepo.findById(req.getDoctorId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found with id: " + req.getDoctorId()));
        AppointmentType type = req.getAppointmentType();
        LocalDateTime end = req.getStartTime().plusMinutes(type.getDurationMinutes());

        // use the same date-range method here
        LocalDate date = req.getStartTime().toLocalDate();
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        List<Appointment> existing = apptRepo.findByDoctorAndDateRange(req.getDoctorId(), startOfDay, endOfDay);

        boolean clash = existing.stream().anyMatch(a -> req.getStartTime().isBefore(a.getEndTime()) && end.isAfter(a.getStart()));
        if (clash) throw new IllegalArgumentException("Slot already booked");

        return apptRepo.save(new Appointment(null, req.getDoctorId(), type, req.getStartTime(), end));
    }
}
