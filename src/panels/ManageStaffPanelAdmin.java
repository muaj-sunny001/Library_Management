package panels;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Librarian;
import fileio.LibrarianFileHandler;


public class ManageStaffPanelAdmin extends JPanel {
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ManageStaffPanelAdmin() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by Uername:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Staff");
        JButton addStaffButton = new JButton("Add Staff");
    

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(deleteButton);
        searchPanel.add(addStaffButton);

        String[] columnNames = { "Username", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        staffTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadStaffData();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStaff();
            }
        });
        addStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStaffFrame();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadStaffData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStaff();
            }
        });

       
    }

    private void loadStaffData() {
        Librarian[] librarians = LibrarianFileHandler.getAllLibrarians();
        tableModel.setRowCount(0);
        for (Librarian librarian : librarians) {
            if (librarian != null) {
                tableModel.addRow(new Object[] {
                        librarian.getUsername(),
                        librarian.getName(),
                        librarian.getEmail()
                });
            }
        }
    }

    private void searchStaff() {
        String searchUsername = searchField.getText().trim();
        if (searchUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Username to search.");
            return;
        }

        tableModel.setRowCount(0);
        Librarian librarian = LibrarianFileHandler.findLibrarianByUsername(searchUsername);
        if (librarian != null) {
            tableModel.addRow(new Object[] {
                    librarian.getUsername(),
                    librarian.getName(),
                    librarian.getEmail()
            });
        } else {
            JOptionPane.showMessageDialog(this, "No Staff found with Username: " + searchUsername);
        }
    }

    private void deleteStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a Staff to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = tableModel.getValueAt(selectedRow, 0).toString();
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this account?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            LibrarianFileHandler.deleteLibrarian(username);
            tableModel.removeRow(selectedRow); // Remove the row from the table
            JOptionPane.showMessageDialog(this, "Librarian account deleted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //..................

    private void addStaffFrame() {
        JFrame frame = new JFrame("Add New Staff");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(50, 30, 100, 25);
        frame.add(userNameLabel);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(160, 30, 150, 25);
        frame.add(userNameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 70, 100, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(160, 70, 150, 25);
        frame.add(passwordField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 110, 100, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(160, 110, 150, 25);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 150, 100, 25);
        frame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(160, 150, 150, 25);
        frame.add(emailField);


        JButton addButton = new JButton("Add Staff");
        addButton.setBounds(100, 190, 100, 30);
        frame.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(210, 190, 100, 30);
        frame.add(cancelButton);

        addButton.addActionListener(e -> {
            String username = userNameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Librarian librarian= LibrarianFileHandler.findLibrarianByUsername(username);
            if (librarian!=null) {
                JOptionPane.showMessageDialog(frame, "This username alredy exist. Try another one.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Librarian librarian2 = new Librarian(username, password, name, email);
            LibrarianFileHandler.addLibrarian(librarian2);

            JOptionPane.showMessageDialog(frame, "Librarian added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    
}    
