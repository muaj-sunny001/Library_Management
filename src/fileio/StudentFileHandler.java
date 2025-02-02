package fileio;
import entities.Student;

import java.io.*;

public class StudentFileHandler {

    private static final String STUDENT_FILE = "resources/Student.txt";

    public static void addStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE, true))) {
            writer.write(student.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to Student file: " + e.getMessage());
        }
    }

    public static void deleteStudent(String userName) {
        File inputFile = new File(STUDENT_FILE);
        StringBuilder updatedData = new StringBuilder();
        boolean studentFound = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromFileFormat(line);
                if (student.getUsername().equals(userName)) {
                    studentFound = true; 
                } else {
                    updatedData.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Student file: " + e.getMessage());
            return;
        }
    
        if (!studentFound) {
            System.out.println("Student with username " + userName + " not found.");
            return;
        }
    
       
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error writing updated Student file: " + e.getMessage());
        }
    
        System.out.println("Student with username " + userName + " has been deleted.");
    }

    public static void updateProfile(String loggedInUser_Username,Student updatedStudent) {
        File inputFile = new File(STUDENT_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromFileFormat(line);
                if (student.getUsername().equals(loggedInUser_Username)) {
                    // Replace the existing student's record with the updated one
                    student = updatedStudent;
                }
                updatedData.append(student.toString()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading Student file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.err.println("Error writing updated Student file: " + e.getMessage());
        }
    }

    public static void updateBorrowcount(String username,int borrowCount) {
        File inputFile = new File(STUDENT_FILE);
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromFileFormat(line);
                if (student.getUsername().equals(username)) {
                    student.updateBorrowedCount(borrowCount);
                }
                updatedData.append(student.toString()).append(System.lineSeparator());
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

    public static Student[] getAllStudents() {
        int size = countLinesInFile();
        Student[] students = new Student[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                students[index++] = Student.fromFileFormat(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading Student file: " + e.getMessage());
        }
        return students;
    }

    public static Student findStudentByUsername(String username) {
        Student[] students = getAllStudents();
        for (Student student : students) {
            if (student != null && student.getUsername().equals(username)) {
                return student;
            }
        }
        return null; 
    }

    private static int countLinesInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in Student file: " + e.getMessage());
        }
        return count;
    }
}

