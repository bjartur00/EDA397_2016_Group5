package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by Þórhildur on 9.4.2016.
 */
public class User {
    String fullName;
    String username;

    public User(String fullName, String username) {

        this.fullName = fullName;
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
