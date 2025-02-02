package frames;
import panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianDashboardFrame extends JFrame {
    
    private JPanel contentPanel;
    private JPanel logoPanel, navigationPanel;
    private JButton logoutButton;
    private JButton manageBookButton, manageMemberButton, overdueButton;

    public LibrarianDashboardFrame() {
        setTitle("Librarian Dashboard");
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

        
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(860, 10, 100, 40); 
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(30, 144, 255)); 
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoPanel.add(logoutButton);

        
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

        
        manageBookButton = createNavigationButton("Manage Books");
        manageMemberButton = createNavigationButton("Manage Members");
        overdueButton = createNavigationButton("Overdue And Fines");

        
        navigationPanel.add(manageBookButton);
        navigationPanel.add(manageMemberButton);
        navigationPanel.add(overdueButton);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBounds(0, 110, 1000, 590); 
        contentPanel.setBackground(new Color(255, 255, 255)); 

        contentPanel.add(new ManageBookPanel(), "Manage Books"); 
        contentPanel.add(new ManageMemberPanel(),"Manage Members");
        contentPanel.add(new OverduePanel(),"Overdue And Fines");
        
       
        manageBookButton.addActionListener(e -> switchPanel("Manage Books") );
        manageMemberButton.addActionListener(e -> switchPanel("Manage Members"));
        overdueButton.addActionListener(e -> switchPanel("Overdue And Fines"));

        
        add(logoPanel);
        add(navigationPanel);
        add(contentPanel);

        switchPanel("Manage Books");
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
            case "Manage Books":
                manageBookButton.setBackground(new Color(30, 144, 255)); 
                break;
            case "Manage Members":
                manageMemberButton.setBackground(new Color(30, 144, 255)); 
                break;
            case "Overdue And Fines":
                overdueButton.setBackground(new Color(30, 144, 255)); 
                break;
        }
    }

    
    private void resetButtonColors() {
        manageBookButton.setBackground(new Color(65, 105, 225));
        manageMemberButton.setBackground(new Color(65, 105, 225));
        overdueButton.setBackground(new Color(65, 105, 225));
    }

}


