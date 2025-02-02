package frames;
import entities.Student;
import fileio.StudentFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RegisterFrame extends JFrame {
    private JTextField usernameField, nameField, emailField, studentIdField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Register");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(230, 230, 250));
        mainPanel.setLayout(null);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBounds(900, 10, 90, 30);

        mainPanel.add(backButton);

        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(320, 50, 400, 50);
        titleLabel.setForeground(new Color(72, 61, 139));
        mainPanel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        usernameLabel.setBounds(250, 150, 150, 30);
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.BOLD, 20));
        usernameField.setBounds(450, 150, 300, 35);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 22));
        passwordLabel.setBounds(250, 210, 150, 30);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.BOLD, 20));
        passwordField.setBounds(450, 210, 300, 35);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setBounds(250, 270, 150, 30);
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.BOLD, 20));
        nameField.setBounds(450, 270, 300, 35);
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 22));
        emailLabel.setBounds(250, 330, 150, 30);
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.BOLD, 20));
        emailField.setBounds(450, 330, 300, 35);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setFont(new Font("Arial", Font.BOLD, 22));
        studentIdLabel.setBounds(250, 390, 150, 30);
        studentIdField = new JTextField(20);
        studentIdField.setFont(new Font("Arial", Font.BOLD, 20));
        studentIdField.setBounds(450, 390, 300, 35);
        mainPanel.add(studentIdLabel);
        mainPanel.add(studentIdField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 24));
        registerButton.setBounds(450, 460, 200, 50);

        mainPanel.add(registerButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String studentId = studentIdField.getText().trim();
        int bookBorrowedCount=0;

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student existingStudent = StudentFileHandler.findStudentByUsername(username);
        if (existingStudent != null) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose another one.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student newStudent= new Student(username, password, name, email, studentId, bookBorrowedCount);
        StudentFileHandler.addStudent(newStudent);

        JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        nameField.setText("");
        emailField.setText("");
        studentIdField.setText("");
       
    }
}
