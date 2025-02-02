package fileio;
import entities.Librarian;

import java.io.*;

public class LibrarianFileHandler {
    private static final String LIBRARIAN_FILE = "resources/Librarian.txt";

    public static void addLibrarian(Librarian librarian) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LIBRARIAN_FILE, true))) {
            writer.write(librarian.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to Librarian file: " + e.getMessage());
        }
    }

    public static void deleteLibrarian(String username) {
        File inputFile = new File(LIBRARIAN_FILE);
        StringBuilder updatedData = new StringBuilder();
        boolean LibrarianFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Librarian librarian = Librarian.fromFileFormat(line);
                if (librarian.getUsername().equals(username)) {
                    LibrarianFound = true;
                }else{
                    updatedData.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Librarian file: " + e.getMessage());
            return;
        }

        if (!LibrarianFound) {
            System.out.println("Librarian with username " + username + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            System.out.println("Error updating Librarian file: " + e.getMessage());
        }

    }

    public static Librarian[] getAllLibrarians() {
        int size = countLinesInFile();
        Librarian[] librarians = new Librarian[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(LIBRARIAN_FILE))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                librarians[index++] = Librarian.fromFileFormat(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading Librarian file: " + e.getMessage());
        }
        return librarians;
    }

    public static Librarian findLibrarianByUsername(String username) {
        Librarian[] librarians = getAllLibrarians();
        for (Librarian librarian : librarians) {
            if (librarian != null && librarian.getUsername().equals(username)) {
                return librarian;
            }
        }
        return null;
    }

    private static int countLinesInFile() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(LIBRARIAN_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error counting lines in Librarians file: " + e.getMessage());
        }
        return count;
    }
}


