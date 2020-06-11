package persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Word {
    @javax.persistence.Id
    @GeneratedValue
    private Integer Id;
    @Column
    private String englishValue;
    @Column
    private String ukrainianValue;

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
        return ukrainianValue;
    }

    public void setWordInUkrainian(String wordInUkrainian) {
        this.ukrainianValue = wordInUkrainian;
    }

    public Word(Integer id, String englishValue, String ukrainianValue) {
        Id = id;
        this.englishValue = englishValue;
        this.ukrainianValue = ukrainianValue;
    }
}
