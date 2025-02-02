package panels;
import entities.Book;
import fileio.BookListFileHandler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookCollectionPanel extends JPanel {
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public BookCollectionPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by ISBN:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        String[] columnNames = { "Book Title", "ISBN", "Author", "Publication"};
        tableModel = new DefaultTableModel(columnNames, 0);
        booksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(booksTable);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadBookData();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadBookData();
            }
        });
        
    }

    private void loadBookData() {
        Book[] books = BookListFileHandler.getAllBooks();
        tableModel.setRowCount(0);
        for (Book book : books) {
            if (book != null) {
                tableModel.addRow(new Object[] {
                        book.getBookTitle(),
                        book.getIsbn(),
                        book.getAuthor(),
                        book.getPublication(),
                });
            }
        }
    }

    private void searchBook() {
        String searchIsbn = searchField.getText().trim();
        if (searchIsbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ISBN to search.");
            return;
        }

        tableModel.setRowCount(0);
        Book book = BookListFileHandler.findBookByIsbn(searchIsbn);
        if (book != null) {
            tableModel.addRow(new Object[] {
                    book.getBookTitle(),
                    book.getIsbn(),
                    book.getAuthor(),
                    book.getPublication(),
                    book.getInventory()
            });
        } else {
            JOptionPane.showMessageDialog(this, "No book found with ISBN: " + searchIsbn);
        }
    }
}
