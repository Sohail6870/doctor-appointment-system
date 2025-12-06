package com.marktine.calendar.controller;

import com.marktine.calendar.dto.CreateAppointmentRequest;
import com.marktine.calendar.model.Appointment;
import com.marktine.calendar.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public Appointment book(@Valid @RequestBody CreateAppointmentRequest req) {
        return service.book(req);
    }
}