package panels;
import entities.Borrow;
import fileio.BorrowFileHandler;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OverduePanel extends JPanel {
    
    private JTable borrowedTable;
    private DefaultTableModel tableModel;

    public OverduePanel(){
        setLayout(new BorderLayout());

        String[] columnNames = { "BorrowedID", "Username","Book Title", "Due Date", "Fines" };
        tableModel = new DefaultTableModel(columnNames, 0);
        borrowedTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(borrowedTable);

        add(scrollPane, BorderLayout.CENTER);

        loadBorrowedBookData();
    }

    private void loadBorrowedBookData() {
        Borrow[] borrows = BorrowFileHandler.getOverdueBorrowsWithFines();
       
        tableModel.setRowCount(0);
        for (Borrow borrow : borrows) {
            if (borrow != null) {
                tableModel.addRow(new Object[] {
                        borrow.getBorrowingId(),
                        borrow.getUserName(),
                        borrow.getBookTitle(),
                        borrow.getDueDate(),
                        borrow.getFines()
                });
            }
        }
    }
}

