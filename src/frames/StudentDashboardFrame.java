package frames;
import entities.Student;
import panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboardFrame extends JFrame {
    private JPanel contentPanel;
    private JPanel logoPanel, navigationPanel;
    private JButton logoutButton,profileButton;
    private JButton bookCollectionButton, borrowButton, returnButton;
    private Student loggedInUser;

    public StudentDashboardFrame(Student loggedInUser) {
        this.loggedInUser = loggedInUser;
        
        setTitle("Student Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); 

        logoPanel = new JPanel();
        logoPanel.setBounds(0, 0, 1000, 60); 
        logoPanel.setLayout(null);
        logoPanel.setBackground(new Color(0, 51, 102)); 

        JLabel libraryName = new JLabel("Students Library");
        libraryName.setFont(new Font("Arial", Font.BOLD, 24));
        libraryName.setForeground(Color.WHITE);
        libraryName.setBounds(20, 10, 300, 40); 
        logoPanel.add(libraryName);

        profileButton = new JButton("Profile");
        profileButton.setBounds(720, 10, 130, 40);
        profileButton.setFont(new Font("Arial", Font.BOLD, 16));
        profileButton.setBackground(new Color(30, 144, 255)); 
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        profileButton.setOpaque(true);
        logoPanel.add(profileButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(860, 10, 100, 40); 
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(30, 144, 255)); 
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoPanel.add(logoutButton);

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  
                new ProfileUpdateFrame(loggedInUser);  
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  
                new LoginFrame();  
            }
        });

        navigationPanel = new JPanel();
        navigationPanel.setBounds(0, 60, 1000, 50); 
        navigationPanel.setLayout(new GridLayout(1, 3, 10, 0)); 
        navigationPanel.setBackground(new Color(100, 149, 237)); 

        bookCollectionButton = createNavigationButton("Book Collection");
        borrowButton = createNavigationButton("Borrow");
        returnButton = createNavigationButton("Return");
        
        navigationPanel.add(bookCollectionButton);
        navigationPanel.add(borrowButton);
        navigationPanel.add(returnButton);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBounds(0, 110, 1000, 590); 
        contentPanel.setBackground(new Color(255, 255, 255)); 

        contentPanel.add(new BookCollectionPanel(), "Book Collection"); 
        contentPanel.add(new BorrowPanel(loggedInUser),"Borrow");
        contentPanel.add(new ReturnPanel(loggedInUser.getUsername()),"Return");
        
        bookCollectionButton.addActionListener(e -> switchPanel("Book Collection") );
        borrowButton.addActionListener(e -> switchPanel("Borrow"));
        returnButton.addActionListener(e -> switchPanel("Return"));

        add(logoPanel);
        add(navigationPanel);
        add(contentPanel);

        switchPanel("Book Collection");
        setVisible(true);
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(65, 105, 225)); 
        button.setForeground(Color.WHITE); 
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private void switchPanel(String section) {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, section);
        
        resetButtonColors();

        switch (section) {
            case "Book Collection":
                bookCollectionButton.setBackground(new Color(30, 144, 255)); 
                break;
            case "Borrow":
                borrowButton.setBackground(new Color(30, 144, 255)); 
                break;
            case "Return":
                returnButton.setBackground(new Color(30, 144, 255)); 
                break;
        }
    }

    private void resetButtonColors() {
        bookCollectionButton.setBackground(new Color(65, 105, 225));
        borrowButton.setBackground(new Color(65, 105, 225));
        returnButton.setBackground(new Color(65, 105, 225));
    }

}
