package com.mycompany.vetsurgeryappv2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class representing a pet.
 */
public class Pet {
    private String name;
    private Date registrationDate;
    private String breed;
    private String owner;
    private Appointment appointment;

    /**
     * Constructor to initialize a pet.
     * @param name the name of the pet.
     * @param registrationDate the registration date of the pet.
     * @param breed the breed of the pet.
     * @param owner the owner of the pet.
     */
    public Pet(String name, Date registrationDate, String breed, String owner) {
        this.name = name;
        this.registrationDate = registrationDate;
        this.breed = breed;
        this.owner = owner;
        this.appointment = null;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    /**
     * Converts the pet details to a string format suitable for file storage.
     * @return the string representation of the pet.
     */
    public String toFileFormat() {
        return name + "\n" + breed + "\n" + owner + "\n" + new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").format(registrationDate);
    }
}
