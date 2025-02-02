package entities;

public class Librarian extends User {

    public Librarian(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static Librarian fromFileFormat(String data) {
        String[] parts = data.split(",");
        return new Librarian(parts[0], parts[1], parts[2], parts[3]);
    }
}

