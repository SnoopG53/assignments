package assignment2;
import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PersonDirectory personDirectory;
    private UpdatePersonPanel updatePersonPanel;

    public MainJFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        personDirectory = new PersonDirectory();
        updatePersonPanel = new UpdatePersonPanel(personDirectory, this);

        setLayout(new BorderLayout());
        add(createSidePanel(), BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(new CreatePersonPanel(personDirectory, this), "CreatePerson");
        mainPanel.add(new ListPersonPanel(personDirectory, this), "ListPerson");
        mainPanel.add(updatePersonPanel, "UpdatePerson");

        setTitle("Person Profile");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(Color.ORANGE);

        JLabel titleLabel = new JLabel("Person Profile");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton btnAddPerson = new JButton("Add Person");
        JButton btnListPerson = new JButton("List Person");
        JTextField searchField = new JTextField("Type name or street address", 15);
        JButton btnSearchPerson = new JButton("Search for Person");

        btnAddPerson.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnListPerson.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSearchPerson.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAddPerson.setMaximumSize(new Dimension(160, 30));
        btnListPerson.setMaximumSize(new Dimension(160, 30));
        searchField.setMaximumSize(new Dimension(160, 30));
        btnSearchPerson.setMaximumSize(new Dimension(160, 30));

        btnAddPerson.addActionListener(e -> cardLayout.show(mainPanel, "CreatePerson"));
        btnListPerson.addActionListener(e -> {
            cardLayout.show(mainPanel, "ListPerson");
            ((ListPersonPanel) mainPanel.getComponent(1)).updateTableData();
        });
        btnSearchPerson.addActionListener(e -> {
            String query = searchField.getText().trim();
            Person foundPerson = personDirectory.searchPerson(query);
            if (foundPerson != null) {
                updatePersonPanel.setPersonToUpdate(foundPerson);
                cardLayout.show(mainPanel, "UpdatePerson");
            } else {
                JOptionPane.showMessageDialog(this, "No person found with the given details.");
            }
        });

        sidePanel.add(titleLabel);
        sidePanel.add(btnAddPerson);
        sidePanel.add(btnListPerson);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(searchField);
        sidePanel.add(btnSearchPerson);
        sidePanel.add(Box.createVerticalGlue());

        return sidePanel;
    }
}