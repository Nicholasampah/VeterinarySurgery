package com.mycompany.vetsurgeryappv2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *This class represents the veterinary surgery application GUI
 * It allows the user to interact with various components of the application like manage pets, book appointments and save data to a file 
 * @author nicholas ampah
 */

public class VetSurgeryAppV2 extends JFrame {
    private Store store;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private JTextField nameField, breedField, ownerField, searchField;
    private final JSpinner registrationDateSpinner;
    private File file, appointmentFile;
    private int currentIndex;
    private int editIndex = -1;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton bookAppointmentButton;
    
    /**
     * Constructor for the VetSurgeryAppV2 class.
     * Initializes the UI components and loads data from the file.
     */

    public VetSurgeryAppV2() {
        store = new Store();
        file = new File("pets.txt");
        appointmentFile = new File("appointments.txt");
        currentIndex = -1;

        setTitle("Nicholas Ampah Veterinary Surgery");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create UI elements
        nameField = new JTextField(20);
        breedField = new JTextField(20);
        ownerField = new JTextField(20);
        searchField = new JTextField(20);
        registrationDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(registrationDateSpinner, "dd/MM/yyyy HH:mm:ss");
        registrationDateSpinner.setEditor(dateEditor);

        tableModel = new DefaultTableModel(new Object[]{"Name", "Breed", "Owner", "Registration Date"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        addButton = new JButton("Add Pet");
        JButton saveButton = new JButton("Save to File");
        JButton loadButton = new JButton("Load from File");
        JButton showButton = new JButton("Show Next Record");
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset Forms");
        editButton = new JButton("Edit Pet");
        bookAppointmentButton = new JButton("Book Appointment");
        editButton.setVisible(false);
        bookAppointmentButton.setVisible(false);

        // Layout setup
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Breed:"));
        inputPanel.add(breedField);
        inputPanel.add(new JLabel("Owner:"));
        inputPanel.add(ownerField);
        inputPanel.add(new JLabel("Registration Date:"));
        inputPanel.add(registrationDateSpinner);
        inputPanel.add(addButton);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search by Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(showButton);
        buttonPanel.add(editButton);
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(resetButton);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(searchPanel, BorderLayout.EAST);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editIndex == -1) {
                    addPet();
                } else {
                    updatePet();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextRecord();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPet();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableEdit(editIndex);
            }
        });

        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookAppointment();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForms();
            }
        });

        // Load data from file on startup
        loadFromFile();

        // Save data to file on exit
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveToFile();
                super.windowClosing(e);
            }
        });
    }

    /**
     * Pet method to add new pet to the store
     */ 
    private void addPet() {
        String name = nameField.getText().trim();
        String breed = breedField.getText().trim();
        String owner = ownerField.getText().trim();
        Date registrationDate = (Date) registrationDateSpinner.getValue();

        Pet newPet = new Pet(name, registrationDate, breed, owner);
        store.addPet(newPet);

        tableModel.addRow(new Object[]{newPet.getName(), newPet.getBreed(), newPet.getOwner(), newPet.getRegistrationDate()});

        clearInputFields();
    }

    /**
     * Pet method to update pet data in the store and also update pet data in the table
     */ 
    private void updatePet() {
        if (editIndex != -1) {
            Pet pet = store.getPet(editIndex);
            pet.setName(nameField.getText().trim());
            pet.setBreed(breedField.getText().trim());
            pet.setOwner(ownerField.getText().trim());
            pet.setRegistrationDate((Date) registrationDateSpinner.getValue());

            tableModel.setValueAt(pet.getName(), editIndex, 0);
            tableModel.setValueAt(pet.getBreed(), editIndex, 1);
            tableModel.setValueAt(pet.getOwner(), editIndex, 2);
            tableModel.setValueAt(pet.getRegistrationDate(), editIndex, 3);

            clearInputFields();
            editIndex = -1;
            addButton.setText("Add Pet");
            editButton.setVisible(false);
            bookAppointmentButton.setVisible(false);
        }
    }
    
    /**
     * Validates the pet details entered by the user.
     * returns true if the details are valid, false otherwise.
     */
    private boolean validatePetDetails() {
        if (nameField.getText().trim().isEmpty() || !nameField.getText().trim().matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Invalid name. Please enter a valid pet name.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (breedField.getText().trim().isEmpty() || !breedField.getText().trim().matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Invalid breed. Please enter a valid breed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ownerField.getText().trim().isEmpty() || !ownerField.getText().trim().matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Invalid owner name. Please enter a valid owner name.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Date registrationDate = (Date) registrationDateSpinner.getValue();
        if (registrationDate.after(new Date())) {
            JOptionPane.showMessageDialog(this, "Invalid registration date. The date cannot be in the future.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    

    /**
    *Method to save pet data to file
    */
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < store.getPetCount(); i++) {
                Pet pet = store.getPet(i);
                writer.println(pet.toFileFormat());
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();  // Logging the error for debugging purposes
        }
    }

    /**
     * Method to load pet data from file and display them in a table
     */
    private void loadFromFile() {
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            store = new Store();
            tableModel.setRowCount(0);
            currentIndex = -1;

            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                String breed = scanner.nextLine().trim();
                String owner = scanner.nextLine().trim();
                Date registrationDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(scanner.nextLine().trim());

                Pet pet = new Pet(name, registrationDate, breed, owner);
                store.addPet(pet);
                tableModel.addRow(new Object[]{pet.getName(), pet.getBreed(), pet.getOwner(), pet.getRegistrationDate()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to display the next pet record in the table.
     */
    private void showNextRecord() {
        if (store.getPetCount() == 0) {
            JOptionPane.showMessageDialog(this, "No records to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        currentIndex = (currentIndex + 1) % store.getPetCount();
        Pet pet = store.getPet(currentIndex);
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{pet.getName(), pet.getBreed(), pet.getOwner(), pet.getRegistrationDate()});
    }

    /**
     * Searches for a pet by name and enables editing if found.
     */
    private void searchPet() {
        String searchName = searchField.getText().trim();
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < store.getPetCount(); i++) {
            Pet pet = store.getPet(i);
            if (pet.getName().equalsIgnoreCase(searchName)) {
                enableEdit(i);
                loadAppointmentsForPet(pet);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No pet found with the name: " + searchName, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Enables the editing mode for the pet at the specified index.
     *
     * @param index The index of the pet to be edited.
     */
    private void enableEdit(int index) {
        Pet pet = store.getPet(index);
        nameField.setText(pet.getName());
        breedField.setText(pet.getBreed());
        ownerField.setText(pet.getOwner());
        registrationDateSpinner.setValue(pet.getRegistrationDate());
        editIndex = index;
        addButton.setText("Update Pet");
        editButton.setVisible(true);
        bookAppointmentButton.setVisible(true);
    }

    
    /**
     * Opens a new window to book an appointment for the selected pet.
     */
    private void bookAppointment() {
        Pet pet = store.getPet(editIndex);
        JFrame appointmentFrame = new JFrame("Book Appointment for " + pet.getName());
        appointmentFrame.setSize(400, 300);

        JTextField doctorNameField = new JTextField(20);
        JTextField reasonField = new JTextField(20);
        JSpinner appointmentDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(appointmentDateSpinner, "dd/MM/yyyy HH:mm:ss");
        appointmentDateSpinner.setEditor(dateEditor);

        JButton saveAppointmentButton = new JButton("Save Appointment");

        JPanel appointmentPanel = new JPanel(new GridLayout(4, 2));
        appointmentPanel.add(new JLabel("Doctor Name:"));
        appointmentPanel.add(doctorNameField);
        appointmentPanel.add(new JLabel("Reason:"));
        appointmentPanel.add(reasonField);
        appointmentPanel.add(new JLabel("Appointment Date:"));
        appointmentPanel.add(appointmentDateSpinner);
        appointmentPanel.add(saveAppointmentButton);

        appointmentFrame.add(appointmentPanel);
        appointmentFrame.setVisible(true);

        saveAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String doctorName = doctorNameField.getText().trim();
                String reason = reasonField.getText().trim();
                Date appointmentDate = (Date) appointmentDateSpinner.getValue();

                try (PrintWriter writer = new PrintWriter(new FileWriter(appointmentFile, true))) {
                    writer.println(pet.getName());
                    writer.println(doctorName);
                    writer.println(reason);
                    writer.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(appointmentDate));
                    JOptionPane.showMessageDialog(appointmentFrame, "Appointment booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(appointmentFrame, "Error saving appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                appointmentFrame.dispose();
            }
        });
    }

    
    /**
     * Opens a new window to book an appointment for the selected pet.
     */
    private void loadAppointmentsForPet(Pet pet) {
        if (!appointmentFile.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(appointmentFile)) {
            StringBuilder appointments = new StringBuilder();
            while (scanner.hasNextLine()) {
                String petName = scanner.nextLine().trim();
                if (petName.equalsIgnoreCase(pet.getName())) {
                    String doctorName = scanner.nextLine().trim();
                    String reason = scanner.nextLine().trim();
                    Date appointmentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(scanner.nextLine().trim());
                    appointments.append(String.format("Doctor: %s, Reason: %s, Date: %s\n", doctorName, reason, appointmentDate));
                }
            }
            if (appointments.length() > 0) {
                JOptionPane.showMessageDialog(this, appointments.toString(), "Appointments for " + pet.getName(), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No appointments found for " + pet.getName(), "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    /**
     * Clears the input fields and resets the button text.
     */
    private void clearInputFields() {
        nameField.setText("");
        breedField.setText("");
        ownerField.setText("");
        registrationDateSpinner.setValue(new Date());
        addButton.setText("Add Pet");
        editButton.setVisible(false);
        bookAppointmentButton.setVisible(false);
    }

    /**
     * Resets all forms and fields.
     */

    private void resetForms() {
        clearInputFields();
        tableModel.setRowCount(0);
        currentIndex = -1;
        editIndex = -1;
        addButton.setText("Add Pet");
    }

    /**
     * The main method to launch the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VetSurgeryAppV2().setVisible(true);
            }
        });
    }
}
