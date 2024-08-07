/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicholas ampah
 */
package com.mycompany.vetsurgeryappv2;

import java.util.ArrayList;

/**
 * Class to manage the collection of pets.
 */
public class Store {
    private ArrayList<Pet> pets;

    /**
     * Constructor to initialize the store.
     */
    public Store() {
        pets = new ArrayList<>();
    }

    /**
     * Adds a pet to the store.
     * @param pet the pet to add.
     */
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    /**
     * Returns the number of pets in the store.
     * @return the number of pets.
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Retrieves a pet by index.
     * @param index the index of the pet.
     * @return the pet at the specified index.
     */
    public Pet getPet(int index) {
        return pets.get(index);
    }
}
