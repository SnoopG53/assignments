package assignment2;
import javax.swing.*;
import java.awt.*;

public class UpdatePersonPanel extends JPanel {
    private Person currentPerson;
    private PersonDirectory personDirectory;

    private JTextField firstNameField, lastNameField, ssnField, emailField, ageField, heightField, weightField, genderField;
    private JTextField homeStreetField, homeUnitField, homeCityField, homeStateField, homeZipField, homePhoneField;
    private JTextField workStreetField, workUnitField, workCityField, workStateField, workZipField, workPhoneField;

    public UpdatePersonPanel(PersonDirectory personDirectory, MainJFrame mainFrame) {
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

        // Address
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        add(createAddressPanel("Home Address", true), gbc);

        gbc.gridy = 5;
        add(createAddressPanel("Work Address", false), gbc);

        // Update button
        gbc.gridy = 6; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updatePerson());
        add(updateButton, gbc);
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

    public void setPersonToUpdate(Person person) {
        this.currentPerson = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        ssnField.setText(person.getSocialSecurityNumber());
        emailField.setText(person.getEmail());
        ageField.setText(String.valueOf(person.getAge()));
        heightField.setText(String.valueOf(person.getHeight()));
        weightField.setText(String.valueOf(person.getWeight()));
        genderField.setText(String.valueOf(person.getGender()));

        Address homeAddress = person.getHomeAddress();
        homeStreetField.setText(homeAddress.getStreetAddress());
        homeUnitField.setText(homeAddress.getUnitNumber());
        homeCityField.setText(homeAddress.getCity());
        homeStateField.setText(homeAddress.getState());
        homeZipField.setText(homeAddress.getZipCode());
        homePhoneField.setText(String.valueOf(homeAddress.getPhoneNumber()));

        Address workAddress = person.getWorkAddress();
        workStreetField.setText(workAddress.getStreetAddress());
        workUnitField.setText(workAddress.getUnitNumber());
        workCityField.setText(workAddress.getCity());
        workStateField.setText(workAddress.getState());
        workZipField.setText(workAddress.getZipCode());
        workPhoneField.setText(String.valueOf(workAddress.getPhoneNumber()));
    }

    private void updatePerson() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String ssn = ssnField.getText().trim();
            String email = emailField.getText().trim();
            String ageText = ageField.getText().trim();
            String heightText = heightField.getText().trim();
            String weightText = weightField.getText().trim();
            String genderText = genderField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || ssn.isEmpty() || email.isEmpty() ||
                    ageText.isEmpty() || heightText.isEmpty() || weightText.isEmpty() || genderText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int age = Integer.parseInt(ageText);
            double height = Double.parseDouble(heightText);
            double weight = Double.parseDouble(weightText);
            char gender = genderText.charAt(0);

            if (age < 0 || height < 0 || weight < 0) {
                JOptionPane.showMessageDialog(this, "Age, height, and weight must be positive values.");
                return;
            }

            if (gender != 'M' && gender != 'F') {
                JOptionPane.showMessageDialog(this, "Gender must be 'M' or 'F'.");
                return;
            }

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

            if (homeStreet.isEmpty() || homeCity.isEmpty() || homeState.isEmpty() || homeZip.isEmpty() ||
                    workStreet.isEmpty() || workCity.isEmpty() || workState.isEmpty() || workZip.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all address fields.");
                return;
            }

            currentPerson.setFirstName(firstName);
            currentPerson.setLastName(lastName);
            currentPerson.setSocialSecurityNumber(ssn);
            currentPerson.setEmail(email);
            currentPerson.setAge(age);
            currentPerson.setHeight(height);
            currentPerson.setWeight(weight);
            currentPerson.setGender(gender);

            Address homeAddress = currentPerson.getHomeAddress();
            homeAddress.setStreetAddress(homeStreet);
            homeAddress.setUnitNumber(homeUnit);
            homeAddress.setCity(homeCity);
            homeAddress.setState(homeState);
            homeAddress.setZipCode(homeZip);
            homeAddress.setPhoneNumber(homePhone);

            Address workAddress = currentPerson.getWorkAddress();
            workAddress.setStreetAddress(workStreet);
            workAddress.setUnitNumber(workUnit);
            workAddress.setCity(workCity);
            workAddress.setState(workState);
            workAddress.setZipCode(workZip);
            workAddress.setPhoneNumber(workPhone);

            JOptionPane.showMessageDialog(this, "Person updated successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for age, height, weight, and phone numbers.");
        }
    }
}
