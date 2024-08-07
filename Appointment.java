package com.mycompany.vetsurgeryappv2;

import java.util.Date;

/**
 * Class representing an appointment for a pet.
 */
public class Appointment {
    private String doctorName;
    private String reason;
    private Date appointmentDate;

    /**
     * Constructor to initialize an appointment.
     * @param doctorName the name of the doctor.
     * @param reason the reason for the appointment.
     * @param appointmentDate the date of the appointment.
     */
    public Appointment(String doctorName, String reason, Date appointmentDate) {
        this.doctorName = doctorName;
        this.reason = reason;
        this.appointmentDate = appointmentDate;
    }

    // Getters and Setters

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
