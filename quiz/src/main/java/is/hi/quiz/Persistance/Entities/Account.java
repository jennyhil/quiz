package is.hi.quiz.Persistance.Entities;

import javax.persistence.*;
import java.util.ArrayList;
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    public String username;
    public String password;
    public String email;
    public String name;
    //private List<Scores> scores = new ArrayList<>();
    public Boolean isAdmin;

    public Account(String username, String password, String email, String name, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.isAdmin = isAdmin;
    }
    public Account() {

    }
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = isAdmin;
    }
}
