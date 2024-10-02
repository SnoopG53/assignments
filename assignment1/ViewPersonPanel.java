package assignment1;

import javax.swing.*;
import java.awt.*;

public class ViewPersonPanel extends JPanel {
    private Person person;
    private JLabel[] personLabels;

    public ViewPersonPanel(Person person) {
        this.person = person;
        setLayout(new GridLayout(19, 1));

        personLabels = new JLabel[19];
        for (int i = 0; i < personLabels.length; i++) {
            personLabels[i] = new JLabel();
            add(personLabels[i]);
        }
    }

    // update person information
    public void updatePersonDetails() {
        String[] personInfo = {
                "First Name: " + person.getFirstName(),
                "Last Name: " + person.getLastName(),
                "Email: " + person.getEmail(),
                "Phone: " + person.getPhone(),
                "Driver's License: " + person.getDriversLicense(),
                "Social Security Number: " + person.getSocialSecurityNumber(),
                "Address Line 1: " + person.getAddressLine1(),
                "Address Line 2: " + person.getAddressLine2(),
                "City: " + person.getCity(),
                "State: " + person.getState(),
                "ZIP: " + person.getZip(),
                "Telephone Number: " + person.getTelephoneNumber(),
                "Fax Number: " + person.getFaxNumber(),
                "Date of Birth(MM/DD/YY): " + person.getDateOfBirth(),
                "Gender: " + person.getGender(),
                "Occupation: " + person.getOccupation(),
                "Nationality: " + person.getNationality(),
                "Employment: " + person.getEmployment(),
                "Age: " + person.getAge()
        };

        // Update each JLabel
        for (int i = 0; i < personLabels.length; i++) {
            personLabels[i].setText(personInfo[i]); // Set text of each JLabel
        }
    }
}