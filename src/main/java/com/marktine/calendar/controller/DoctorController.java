package com.marktine.calendar.controller;

import com.marktine.calendar.dto.TimeSlotDto;
import com.marktine.calendar.model.AppointmentType;
import com.marktine.calendar.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final AppointmentService service;

    public DoctorController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/{id}/available-slots")
    public List<TimeSlotDto> slots(@PathVariable Long id,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   @RequestParam AppointmentType type) {
        return service.availableSlots(id, date, type);
    }
}