/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.mycompany.vetsurgeryappv2;

/**
 *
 * @author nicholas ampah
*/



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A form to book appointments for pets.
 */
public class AppointmentBookingForm extends JDialog {
    private JTextField doctorNameField;
    private JTextArea reasonField;
    private JSpinner appointmentDateSpinner;
    private Pet pet;
    private File appointmentFile;

    /**
     * Constructor to initialize the Appointment Booking Form.
     * @param parent the parent frame.
     * @param pet the pet for which the appointment is being booked.
     */
    public AppointmentBookingForm(Frame parent, Pet pet) {
        super(parent, "Book Appointment", true);
        this.pet = pet;
        this.appointmentFile = new File("appointments.txt");

        setSize(400, 300);
        setLayout(new GridLayout(5, 2));

        doctorNameField = new JTextField(20);
        reasonField = new JTextArea(3, 20);
        appointmentDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(appointmentDateSpinner, "dd/MM/yyyy HH:mm:ss");
        appointmentDateSpinner.setEditor(dateEditor);

        JButton bookButton = new JButton("Book Appointment");
        JButton cancelButton = new JButton("Cancel");

        add(new JLabel("Doctor Name:"));
        add(doctorNameField);
        add(new JLabel("Reason:"));
        add(new JScrollPane(reasonField));
        add(new JLabel("Appointment Date:"));
        add(appointmentDateSpinner);
        add(bookButton);
        add(cancelButton);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookAppointment();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * Books an appointment for the pet.
     */
    private void bookAppointment() {
        String doctorName = doctorNameField.getText().trim();
        String reason = reasonField.getText().trim();
        Date appointmentDate = (Date) appointmentDateSpinner.getValue();

        if (doctorName.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Appointment appointment = new Appointment(doctorName, reason, appointmentDate);
        pet.setAppointment(appointment);

        // Save appointment details to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(appointmentFile, true))) {
            writer.println(pet.getName());
            writer.println(doctorName);
            writer.println(reason);
            writer.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(appointmentDate));
            JOptionPane.showMessageDialog(this, "Appointment booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving appointment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
