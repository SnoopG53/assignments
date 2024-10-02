package assignment1;

import javax.swing.*;
import java.awt.*;

public class PersonMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Person person;
    private CreatePersonPanel createPanel;
    private ViewPersonPanel viewPanel;

    public PersonMain() {
        person = new Person();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        createPanel = new CreatePersonPanel(person, this::showViewPersonPanel);
        viewPanel = new ViewPersonPanel(person);

        // Add both panels to the cardPanel at the start
        cardPanel.add(createPanel, "CreateProfile");
        cardPanel.add(viewPanel, "ViewProfile");
        // Add the cardPanel to the frame
        add(cardPanel);

        // Set JFrame properties
        setTitle("Create Person Profile");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //switch to the "View Person" panel
    private void showViewPersonPanel() {
        viewPanel.updatePersonDetails();
        cardLayout.show(cardPanel, "ViewProfile"); // Switch to ViewProfile panel after clicking view
    }

    public static void main(String[] args) {
        new PersonMain();
    }
}