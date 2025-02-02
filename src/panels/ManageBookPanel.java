package panels;
import entities.Book;
import fileio.BookListFileHandler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageBookPanel extends JPanel {
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ManageBookPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by ISBN:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Book");
        JButton addBooksButton = new JButton("Add Books");
        JButton updateInventoryButton = new JButton("Update Inventory");
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(deleteButton);
        searchPanel.add(addBooksButton);
        searchPanel.add(updateInventoryButton);

        String[] columnNames = { "Book Title", "ISBN", "Author", "Publication", "Inventory" };
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
        addBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBookFrame();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadBookData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        updateInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateInventoryFrame();
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
                        book.getInventory()
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

    private void deleteBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            return;
        }

        String isbnToDelete = tableModel.getValueAt(selectedRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the selected book?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            BookListFileHandler.deleteBookByIsbn(isbnToDelete);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Book deleted successfully.");
        }
    }

    // ...............

    private void openUpdateInventoryFrame() {
        JFrame updateFrame = new JFrame("Update Inventory");
        updateFrame.setSize(400, 300);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setLayout(null);

        JLabel isbnLabel = new JLabel("Enter ISBN:");
        isbnLabel.setBounds(50, 30, 100, 25);
        updateFrame.add(isbnLabel);

        JTextField isbnField = new JTextField(15);
        isbnField.setBounds(180, 30, 150, 25);
        updateFrame.add(isbnField);

        JLabel inventoryLabel = new JLabel("Change in Inventory:");
        inventoryLabel.setBounds(30, 80, 150, 25);
        updateFrame.add(inventoryLabel);

        JTextField inventoryField = new JTextField(15);
        inventoryField.setBounds(180, 80, 150, 25);
        updateFrame.add(inventoryField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(140, 130, 100, 30);
        updateFrame.add(updateButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(50, 180, 300, 25);
        updateFrame.add(statusLabel);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();
                String inventoryChangeText = inventoryField.getText().trim();

                try {
                    int inventoryChange = Integer.parseInt(inventoryChangeText);
                    BookListFileHandler.updateBookInventory(isbn, inventoryChange);
                    statusLabel.setText("Inventory updated successfully.");
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Invalid inventory value.");
                } catch (Exception ex) {
                    statusLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        updateFrame.setLocationRelativeTo(null);
        updateFrame.setVisible(true);
    }

    // ...............
    private void addBookFrame() {
        JFrame frame = new JFrame("Add New Book");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Book Title:");
        titleLabel.setBounds(50, 30, 100, 25);
        frame.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(160, 30, 150, 25);
        frame.add(titleField);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(50, 70, 100, 25);
        frame.add(isbnLabel);

        JTextField isbnField = new JTextField();
        isbnField.setBounds(160, 70, 150, 25);
        frame.add(isbnField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 110, 100, 25);
        frame.add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(160, 110, 150, 25);
        frame.add(authorField);

        JLabel publicationLabel = new JLabel("Publication:");
        publicationLabel.setBounds(50, 150, 100, 25);
        frame.add(publicationLabel);

        JTextField publicationField = new JTextField();
        publicationField.setBounds(160, 150, 150, 25);
        frame.add(publicationField);

        JLabel inventoryLabel = new JLabel("Inventory:");
        inventoryLabel.setBounds(50, 190, 100, 25);
        frame.add(inventoryLabel);

        JTextField inventoryField = new JTextField();
        inventoryField.setBounds(160, 190, 150, 25);
        frame.add(inventoryField);

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(100, 240, 100, 30);
        frame.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(210, 240, 100, 30);
        frame.add(cancelButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String isbn = isbnField.getText().trim();
            String author = authorField.getText().trim();
            String publication = publicationField.getText().trim();
            String inventoryStr = inventoryField.getText().trim();

            if (title.isEmpty() || isbn.isEmpty() || author.isEmpty() || publication.isEmpty()
                    || inventoryStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int inventory;
            try {
                inventory = Integer.parseInt(inventoryStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Inventory must be a valid number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book book = new Book(title, isbn, author, publication, inventory);
            BookListFileHandler.addBook(book);

            JOptionPane.showMessageDialog(frame, "Book added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
