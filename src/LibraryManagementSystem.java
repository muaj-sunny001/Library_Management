import fileio.BorrowFileHandler;
import frames.LoginFrame;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        BorrowFileHandler.updateFines();
        new LoginFrame();
    }
}
