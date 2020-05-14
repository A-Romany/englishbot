package persistence;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private Integer Id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private ArrayList<Word> allUsersWords;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Word> getAllUsersWords() {
        return allUsersWords;
    }

    public void setAllUsersWords(ArrayList<Word> allUsersWords) {
        this.allUsersWords = allUsersWords;
    }
}
