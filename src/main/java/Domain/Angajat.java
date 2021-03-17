package Domain;

import java.io.Serializable;

public class Angajat implements Serializable {

    private String username;
    private String password;

    public Angajat(String username, String passworf) {
        this.username = username;
        this.password = passworf;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
