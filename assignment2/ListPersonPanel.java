package assignment2;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListPersonPanel extends JPanel {
    private PersonDirectory personDirectory;
    private JTable personTable;

    public ListPersonPanel(PersonDirectory personDirectory, MainJFrame mainFrame) {
        this.personDirectory = personDirectory;
        setLayout(new BorderLayout());
        setBackground(Color.PINK);

        String[] columnNames = {"First Name", "Last Name", "Home City", "Home Zip", "Work City", "Work Zip"};
        personTable = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane scrollPane = new JScrollPane(personTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected Person");
        deleteButton.addActionListener(e -> deleteSelectedPerson());
        add(deleteButton, BorderLayout.SOUTH);
    }

    public void updateTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) personTable.getModel();
        tableModel.setRowCount(0);

        for (Person person : personDirectory.getPersonList()) {
            Object[] rowData = {
                    person.getFirstName(),
                    person.getLastName(),
                    person.getHomeAddress().getCity(),
                    person.getHomeAddress().getZipCode(),
                    person.getWorkAddress().getCity(),
                    person.getWorkAddress().getZipCode()
            };
            tableModel.addRow(rowData);
        }
    }

    private void deleteSelectedPerson() {
        int selectedRow = personTable.getSelectedRow();
        if (selectedRow >= 0) {
            String firstName = (String) personTable.getValueAt(selectedRow, 0);
            String lastName = (String) personTable.getValueAt(selectedRow, 1);
            personDirectory.removePerson(firstName, lastName);
            updateTableData();
            JOptionPane.showMessageDialog(this, "Person deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a person to delete.");
        }
    }
}
