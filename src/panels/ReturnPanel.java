package panels;
import entities.Book;
import entities.Borrow;
import fileio.BookListFileHandler;
import fileio.BorrowFileHandler;
import fileio.StudentFileHandler;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReturnPanel extends JPanel {
    private JTable borrowedTable;
    private DefaultTableModel tableModel;
    private String loggedInUser;

    public ReturnPanel(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton returnBookButton = new JButton("Return Book");

        buttonPanel.add(returnBookButton);

        String[] columnNames = { "BorrowedID", "Book Title", "Borrowed Date", "Due Date", "Fines" };
        tableModel = new DefaultTableModel(columnNames, 0);
        borrowedTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(borrowedTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadBorrowedBookData();

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
    }

    private void loadBorrowedBookData() {
        Borrow[] borrows = BorrowFileHandler.getApprovedRequests(loggedInUser);
        if (borrows == null) {
            borrows = new Borrow[0]; 
        }
        tableModel.setRowCount(0);
        for (Borrow borrow : borrows) {
            if (borrow != null) {
                tableModel.addRow(new Object[] {
                        borrow.getBorrowingId(),
                        borrow.getBookTitle(),
                        borrow.getBorrowingDate(),
                        borrow.getDueDate(),
                        borrow.getFines()
                });
            }
        }
    }

    private void returnBook() {

        int selectedRow = borrowedTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to proceed.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String bookTitle = (String) tableModel.getValueAt(selectedRow, 1);
        Book book = BookListFileHandler.findBookByTitle(bookTitle);

        String borrowingId = (String) tableModel.getValueAt(selectedRow, 0);
        String username = loggedInUser;

        BorrowFileHandler.returnBook(borrowingId);

        StudentFileHandler.updateBorrowcount(username, -1);

        BookListFileHandler.updateBookInventory(book.getIsbn(), 1);

        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Book returned successfully.");

    }

}
