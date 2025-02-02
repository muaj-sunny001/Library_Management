package panels;
import entities.*;
import fileio.BookListFileHandler;
import fileio.BorrowFileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowPanel extends JPanel {
    private JTextField isbnField;
    private JButton borrowButton;
    private JLabel messageLabel;

    private Student loggedInUser;

    public BorrowPanel(Student loggedInUser) {

        setLayout(null);
        this.loggedInUser = loggedInUser; 

        JLabel isbnLabel = new JLabel("Enter ISBN:");
        isbnLabel.setBounds(350, 130, 200, 30);
        isbnLabel.setFont(new Font("Arial", Font.BOLD, 18));

        isbnField = new JTextField(20);
        isbnField.setBounds(480, 130, 200, 30);
        isbnField.setFont(new Font("Arial", Font.BOLD, 18));

        borrowButton = new JButton("Request Borrow");
        borrowButton.setBounds(420, 250, 210, 40);
        borrowButton.setFont(new Font("Arial", Font.BOLD, 18));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(350, 300, 400, 30);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(isbnLabel);
        add(isbnField);
        add(borrowButton);
        add(messageLabel);

        borrowButton.addActionListener(new BorrowButtonListener());
    }

    private class BorrowButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String isbn = isbnField.getText().trim();

            if (isbn.isEmpty()) {
                messageLabel.setText("Please enter an ISBN.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            
            Book book = BookListFileHandler.findBookByIsbn(isbn);

            if (book == null) {
                messageLabel.setText("Book not found. Check ISBN.");
                messageLabel.setForeground(Color.RED);
                return;
            }
            if (book.getInventory()==0) {
                messageLabel.setText("The book is currently not available.");
                messageLabel.setForeground(Color.RED);
                return;
            }
            if (loggedInUser.getBookBorrowedCount()>=5) {
                messageLabel.setText("You can't borrow more than 5 books.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            
            String bookTitle = book.getBookTitle();
            LocalDate borrowingTime = LocalDate.now();
            LocalDate dueDate = borrowingTime.plusWeeks(2); 
           
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            
            String borrowingTimeStr = borrowingTime.format(formatter);
            String dueDateStr = dueDate.format(formatter);

            String approvalStatus = "pending";

            Borrow borrow = new Borrow(loggedInUser.getUsername(), bookTitle, borrowingTimeStr, dueDateStr, approvalStatus, 0.0);

           
            BorrowFileHandler.addBorrowRequest(borrow);

            messageLabel.setText("Borrow request submitted!");
            messageLabel.setForeground(Color.GREEN);

            isbnField.setText("");
        }
    }
}

