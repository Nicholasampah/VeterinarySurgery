/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicholas ampah
 */
package com.mycompany.vetsurgeryappv2;

/**
 * Abstract class representing an animal.
 */
public abstract class Animal {
    private String breed;

    /**
     * Constructor to initialize an animal.
     * @param breed the breed of the animal.
     */
    public Animal(String breed) {
        this.breed = breed;
    }

    // Getter and Setter

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
