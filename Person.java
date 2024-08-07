/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicam
 */
package com.mycompany.vetsurgeryappv2;

/**
 * Class representing a person.
 */
public class Person {
    private String name;

    /**
     * Constructor to initialize a person.
     * @param name the name of the person.
     */
    public Person(String name) {
        this.name = name;
    }

    // Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
