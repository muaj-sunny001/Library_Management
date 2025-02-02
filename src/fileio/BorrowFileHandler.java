package fileio;
import entities.Borrow;

import java.io.*;

public class BorrowFileHandler {
    private static final String BORROW_FILE = "resources/Borrow.txt";
 
    public static void addBorrowRequest(Borrow borrow) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BORROW_FILE, true))) {
            writer.write(borrow.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to borrow file: " + e.getMessage());
        }
    }
    
    public static void updateApprovalStatus(String borrowingId, String newStatus) {
        File inputFile = new File(BORROW_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Borrow borrow = Borrow.fromFileFormat(line);
                if (borrow.getBorrowingId().equals(borrowingId)) {
                    borrow.setApprovalStatus(newStatus);
                }
                updatedData.append(borrow.toString()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading borrow file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error updating borrow file: " + e.getMessage());
        }
    }

    public static void updateUsername(String previousUsername, String newUsername) {
        File inputFile = new File(BORROW_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Borrow borrow = Borrow.fromFileFormat(line);
                if (borrow.getUserName().equals(previousUsername)) {
                    borrow.setUsername(newUsername);
                }
                updatedData.append(borrow.toString()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading borrow file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error updating borrow file: " + e.getMessage());
        }
    }

    public static void returnBook(String borrowingId) {
        File inputFile = new File(BORROW_FILE);
        StringBuilder updatedData = new StringBuilder();
        boolean found = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Borrow borrow = Borrow.fromFileFormat(line);
                if (borrow.getBorrowingId().equals(borrowingId)) {
                    found = true; 
                } else {
                    updatedData.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading borrow file: " + e.getMessage());
            return;
        }
    
        if (!found) {
            System.out.println("Borrowing ID not found.");
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error updating borrow file: " + e.getMessage());
        }
    
        System.out.println("Book returned successfully.");
    }
    

    public static Borrow[] getAllPendingRequests() {
        Borrow[] borrows = getAllBorrowingrequest();
        int count = 0;

        for (Borrow borrow : borrows) {
            if (borrow != null && borrow.getApprovalStatus().equals("pending")) {
                count++;
            }
        }
   
        if (count == 0) {
            return null;
        }
        
        Borrow[] result = new Borrow[count];
        int index = 0;
        
        for (Borrow borrow : borrows) {
            if (borrow != null && borrow.getApprovalStatus().equals("pending")) {
                result[index++] = borrow;
            }
        }
        
        return result;
    }

    public static Borrow[] getApprovedRequests(String userName) {
        Borrow[] borrows = getAllBorrowingrequest();
        int count = 0;

        for (Borrow borrow : borrows) {
            if (borrow != null && borrow.getUserName().equals(userName) && borrow.getApprovalStatus().equals("approved")) {
                count++;
            }
        }
   
        if (count == 0) {
            return new Borrow[0];
        }
        
        Borrow[] result = new Borrow[count];
        int index = 0;
        
        for (Borrow borrow : borrows) {
            if (borrow != null && borrow.getUserName().equals(userName) && borrow.getApprovalStatus().equals("approved")) {
                result[index++] = borrow;
            }
        }
        
        return result;
    }

    public static Borrow[] getOverdueBorrowsWithFines() {
        Borrow[] borrows = getAllBorrowingrequest();
        int overdueCount = 0;
    
        for (Borrow borrow : borrows) {
            if (borrow != null && borrow.isOverdue()) {
                overdueCount++;
            }
        }
    
        if (overdueCount == 0) {
            return new Borrow[0];
        }
    
        Borrow[] overdueBorrowsWithFines = new Borrow[overdueCount];
        int index = 0;
    
        for (Borrow borrow : borrows) {
            if (borrow != null && borrow.isOverdue()) {
                borrow.calculateFines();  
                overdueBorrowsWithFines[index++] = borrow;
            }
        }
    
        return overdueBorrowsWithFines;
    }
    
    public static void updateFines() {
        Borrow[] borrows = getAllBorrowingrequest();

        for (Borrow borrow : borrows) {
            borrow.calculateFines(); 
        }

        updateBorrowRecords(borrows);
    }

    public static void updateBorrowRecords(Borrow[] borrows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BORROW_FILE))) {
            for (Borrow borrow : borrows) {
                bw.write(borrow.toString());
                bw.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public static Borrow[] getAllBorrowingrequest() {
        int size = countLinesInFile();
        Borrow[] borrows = new Borrow[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(BORROW_FILE))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                borrows[index++] = Borrow.fromFileFormat(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading Borrow file: " + e.getMessage());
        }
        return borrows;
    }

    private static int countLinesInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(BORROW_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in borrow file: " + e.getMessage());
        }
        return count;
    }
}
