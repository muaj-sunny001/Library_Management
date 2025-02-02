package entities;

public class Student extends User {
    private String studentId;
    private int bookBorrowedCount;

    public Student(String username, String password, String name, String email, String studentId, int bookBorrowedCount) {
        super(username, password, name, email);
        this.studentId = studentId;
        this.bookBorrowedCount = bookBorrowedCount;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public void setBookBorrowedCount(int bookBorrowedCount) {
        this.bookBorrowedCount = bookBorrowedCount;
    }
    

    public String getStudentId() {
        return studentId;
    }

    public int getBookBorrowedCount() {
        return bookBorrowedCount;
    }

    public void updateBorrowedCount(int bookCount) {
        this.bookBorrowedCount += bookCount;
    }

    @Override
    public String toString() {
        return super.toString() + "," + studentId + "," + bookBorrowedCount;
    }

    public static Student fromFileFormat(String data) {
        String[] parts = data.split(",");
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
    }
}

