package persistence;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table
public class Word {

    @Id
    @GeneratedValue
    private Integer Id;
    @Column
    private String englishValue;
    @Column
    private String wordInUkrainian;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    public String getEnglishValue() {
        return englishValue;
    }

    public void setEnglishValue(String englishValue) {
        this.englishValue = englishValue;
    }

    public String getWordInUkrainian() {
        return wordInUkrainian;
    }

    public void setWordInUkrainian(String wordInUkrainian) {
        this.wordInUkrainian = wordInUkrainian;
    }


//1

    //2

}
