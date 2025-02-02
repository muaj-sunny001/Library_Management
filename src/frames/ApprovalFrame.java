package frames;

import entities.Borrow;
import entities.Book;
import fileio.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApprovalFrame extends JFrame {
    private JTable approvalTable;
    private DefaultTableModel tableModel;

    public ApprovalFrame() {
        setTitle("Approval Dashboard");
        setSize(800, 400);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new String[] { "BorrowingID", "Username", "Book Title", "Approval Status" },
                0);
        approvalTable = new JTable(tableModel);
        approvalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        JButton closeButton = new JButton("Close");

        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleApproval(true);
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleApproval(false);
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(approvalTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadPendingRequests();

        setVisible(true);
    }

    private void loadPendingRequests() {
        Borrow[] pendingRequests = BorrowFileHandler.getAllPendingRequests();
        for (Borrow request : pendingRequests) {
            tableModel.addRow(new Object[] {
                    request.getBorrowingId(),
                    request.getUserName(),
                    request.getBookTitle(),
                    request.getApprovalStatus()
            });
        }
    }

    private void handleApproval(boolean isApproved) {
        int selectedRow = approvalTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to proceed.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String bookTitle = (String) tableModel.getValueAt(selectedRow, 2);
        Book book = BookListFileHandler.findBookByTitle(bookTitle);

        String borrowingId = (String) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        String newStatus;
        if (isApproved) {
            newStatus = "approved";
        } else {
            newStatus = "rejected";
        }

        if (book.getInventory() > 0) {
            BorrowFileHandler.updateApprovalStatus(borrowingId, newStatus);

            if (isApproved) {
                StudentFileHandler.updateBorrowcount(username, 1);
            }
            BookListFileHandler.updateBookInventory(book.getIsbn(), -1);

            tableModel.setValueAt(newStatus, selectedRow, 3);

            JOptionPane.showMessageDialog(this, "Request has been " + newStatus + ".", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        }

    }

}
