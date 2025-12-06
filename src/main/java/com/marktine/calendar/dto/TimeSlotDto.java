package com.marktine.calendar.dto;

import java.time.LocalDateTime;

public class TimeSlotDto {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeSlotDto(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
}