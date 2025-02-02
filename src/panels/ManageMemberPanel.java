package panels;
import entities.Student;
import fileio.StudentFileHandler;
import frames.ApprovalFrame;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ManageMemberPanel extends JPanel {
    private JTable memberTable;
    private DefaultTableModel tableModel;

    public ManageMemberPanel() {
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
    
        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Account");
        JButton aproveBorrowButton = new JButton("Aprove Borrow");

        buttonPanel.add(showAllButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(aproveBorrowButton);

        String[] columnNames = { "Username", "Name", "Email", "Student ID", "Currently Borrowed" };
        tableModel = new DefaultTableModel(columnNames, 0);
        memberTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadStudentData();


        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0); 
                loadStudentData(); 
            }
        });
        aproveBorrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ApprovalFrame(); 
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMember(); 
            }
        }); 
    }
    private void loadStudentData() {
        Student[] students = StudentFileHandler.getAllStudents();
        tableModel.setRowCount(0); 
        for (Student student : students) {
            if (student != null) {
                tableModel.addRow(new Object[] {
                        student.getUsername(),
                        student.getName(),
                        student.getEmail(),
                        student.getStudentId(),
                        student.getBookBorrowedCount()
                });
            }
        }
    }

     private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            return;
        }

        String userName = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the selected member?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            StudentFileHandler.deleteStudent(userName);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Student deleted successfully.");
        }
    }

}
