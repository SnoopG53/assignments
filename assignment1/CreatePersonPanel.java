package assignment1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePersonPanel extends JPanel {
    private JTextField[] fields;
    private Person person;
    private JButton submitButton;
    private JButton viewButton;

    public CreatePersonPanel(Person person, Runnable switchToViewPanel) {
        this.person = person;
        setLayout(new GridLayout(21, 2)); // Adjusted for 19 fields + 2 buttons

        String[] labels = {
                "First Name", "Last Name", "Email", "Phone", "Driver's License", "Social Security Number",
                "Address Line 1", "Address Line 2", "City", "State", "ZIP",
                "Telephone Number", "Fax Number", "Date of Birth(DD/MM/YY)", "Gender", "Occupation",
                "Nationality", "Employment", "Age"
        };

        fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            JTextField field = new JTextField(20);
            fields[i] = field;
            add(label);
            add(field);
        }

        // Submit button: once we click on it, we save all the data
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitPersonDetails();
                JOptionPane.showMessageDialog(null, "Person details saved!");  // Confirmation message
            }
        });
        add(new JLabel());
        add(submitButton);

        // View button: once we click on it, it switches to the view panel and displays the person's details
        viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToViewPanel.run();  // Switch to view panel when "View" button is clicked
            }
        });
        add(new JLabel()); // Placeholder for alignment
        add(viewButton);
    }

    // Function to save form data to the Person object
    private void submitPersonDetails() {
        person.setFirstName(fields[0].getText());
        person.setLastName(fields[1].getText());
        person.setEmail(fields[2].getText());
        person.setPhone(fields[3].getText());
        person.setDriversLicense(fields[4].getText());
        person.setSocialSecurityNumber(fields[5].getText());
        person.setAddressLine1(fields[6].getText());
        person.setAddressLine2(fields[7].getText());
        person.setCity(fields[8].getText());
        person.setState(fields[9].getText());
        person.setZip(fields[10].getText());
        person.setTelephoneNumber(fields[11].getText());
        person.setFaxNumber(fields[12].getText());
        person.setDateOfBirth(fields[13].getText());
        person.setGender(fields[14].getText());
        person.setOccupation(fields[15].getText());
        person.setNationality(fields[16].getText());
        person.setEmployment(fields[17].getText());
        person.setAge((fields[18].getText()));
    }
}