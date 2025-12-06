package com.marktine.calendar.model;

public enum AppointmentType {
    GENERAL_CONSULTATION(30),
    FOLLOW_UP(15),
    PHYSICAL_EXAM(45),
    SPECIALIST_CONSULTATION(60);

    private final int durationMinutes;

    AppointmentType(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}