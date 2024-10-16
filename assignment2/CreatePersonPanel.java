package assignment2;
import javax.swing.*;
import java.awt.*;

public class CreatePersonPanel extends JPanel {
    private PersonDirectory personDirectory;

    private JTextField firstNameField, lastNameField, ssnField, emailField, ageField, heightField, weightField, genderField;
    private JTextField homeStreetField, homeUnitField, homeCityField, homeStateField, homeZipField, homePhoneField;
    private JTextField workStreetField, workUnitField, workCityField, workStateField, workZipField, workPhoneField;

    public CreatePersonPanel(PersonDirectory personDirectory, MainJFrame mainFrame) {
        this.personDirectory = personDirectory;
        setLayout(new GridBagLayout());
        setBackground(Color.PINK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel ssnLabel = new JLabel("SSN");
        JLabel emailLabel = new JLabel("Email");
        JLabel ageLabel = new JLabel("Age");
        JLabel heightLabel = new JLabel("Height (cm)");
        JLabel weightLabel = new JLabel("Weight (kg)");
        JLabel genderLabel = new JLabel("Gender (M/F)");

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        ssnField = new JTextField(15);
        emailField = new JTextField(15);
        ageField = new JTextField(5);
        heightField = new JTextField(5);
        weightField = new JTextField(5);
        genderField = new JTextField(2);

        gbc.gridx = 0; gbc.gridy = 0;
        add(firstNameLabel, gbc);
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 2;
        add(lastNameLabel, gbc);
        gbc.gridx = 3;
        add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(ssnLabel, gbc);
        gbc.gridx = 1;
        add(ssnField, gbc);

        gbc.gridx = 2;
        add(emailLabel, gbc);
        gbc.gridx = 3;
        add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(ageLabel, gbc);
        gbc.gridx = 1;
        add(ageField, gbc);

        gbc.gridx = 2;
        add(heightLabel, gbc);
        gbc.gridx = 3;
        add(heightField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(weightLabel, gbc);
        gbc.gridx = 1;
        add(weightField, gbc);

        gbc.gridx = 2;
        add(genderLabel, gbc);
        gbc.gridx = 3;
        add(genderField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        add(createAddressPanel("Home Address", true), gbc);

        gbc.gridy = 5;
        add(createAddressPanel("Work Address", false), gbc);

        gbc.gridy = 6; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> createPerson());
        add(createButton, gbc);
    }

    private JPanel createAddressPanel(String title, boolean isHomeAddress) {
        JPanel addressPanel = new JPanel(new GridBagLayout());
        addressPanel.setBorder(BorderFactory.createTitledBorder(title));
        addressPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel streetLabel = new JLabel("Street Address");
        JLabel unitLabel = new JLabel("Unit Number");
        JLabel cityLabel = new JLabel("City");
        JLabel stateLabel = new JLabel("State");
        JLabel zipLabel = new JLabel("Zip Code");
        JLabel phoneLabel = new JLabel("Phone Number");

        JTextField streetField = new JTextField(10);
        JTextField unitField = new JTextField(5);
        JTextField cityField = new JTextField(10);
        JTextField stateField = new JTextField(5);
        JTextField zipField = new JTextField(10);
        JTextField phoneField = new JTextField(10);

        gbc.gridx = 0; gbc.gridy = 0;
        addressPanel.add(streetLabel, gbc);
        gbc.gridx = 1;
        addressPanel.add(streetField, gbc);

        gbc.gridx = 2;
        addressPanel.add(unitLabel, gbc);
        gbc.gridx = 3;
        addressPanel.add(unitField, gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        addressPanel.add(cityLabel, gbc);
        gbc.gridx = 1;
        addressPanel.add(cityField, gbc);

        gbc.gridx = 2;
        addressPanel.add(stateLabel, gbc);
        gbc.gridx = 3;
        addressPanel.add(stateField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        addressPanel.add(zipLabel, gbc);
        gbc.gridx = 1;
        addressPanel.add(zipField, gbc);

        gbc.gridx = 2;
        addressPanel.add(phoneLabel, gbc);
        gbc.gridx = 3;
        addressPanel.add(phoneField, gbc);

        if (isHomeAddress) {
            homeStreetField = streetField;
            homeUnitField = unitField;
            homeCityField = cityField;
            homeStateField = stateField;
            homeZipField = zipField;
            homePhoneField = phoneField;
        } else {
            workStreetField = streetField;
            workUnitField = unitField;
            workCityField = cityField;
            workStateField = stateField;
            workZipField = zipField;
            workPhoneField = phoneField;
        }
        return addressPanel;
    }

    private void createPerson() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String ssn = ssnField.getText().trim();
            String email = emailField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            double height = Double.parseDouble(heightField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            char gender = genderField.getText().trim().charAt(0);

            String homeStreet = homeStreetField.getText().trim();
            String homeUnit = homeUnitField.getText().trim();
            String homeCity = homeCityField.getText().trim();
            String homeState = homeStateField.getText().trim();
            String homeZip = homeZipField.getText().trim();
            long homePhone = Long.parseLong(homePhoneField.getText().trim());

            String workStreet = workStreetField.getText().trim();
            String workUnit = workUnitField.getText().trim();
            String workCity = workCityField.getText().trim();
            String workState = workStateField.getText().trim();
            String workZip = workZipField.getText().trim();
            long workPhone = Long.parseLong(workPhoneField.getText().trim());

            Address homeAddress = new Address(homeStreet, homeUnit, homeCity, homeState, homeZip, homePhone);
            Address workAddress = new Address(workStreet, workUnit, workCity, workState, workZip, workPhone);

            Person newPerson = new Person(firstName, lastName, ssn, email, age, height, weight, gender, homeAddress, workAddress);
            personDirectory.addPerson(newPerson);

            JOptionPane.showMessageDialog(this, "Person created successfully.");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid data for age, height, weight, and phone numbers.");
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ssnField.setText("");
        emailField.setText("");
        ageField.setText("");
        heightField.setText("");
        weightField.setText("");
        genderField.setText("");
        homeStreetField.setText("");
        homeUnitField.setText("");
        homeCityField.setText("");
        homeStateField.setText("");
        homeZipField.setText("");
        homePhoneField.setText("");
        workStreetField.setText("");
        workUnitField.setText("");
        workCityField.setText("");
        workStateField.setText("");
        workZipField.setText("");
        workPhoneField.setText("");
    }
}