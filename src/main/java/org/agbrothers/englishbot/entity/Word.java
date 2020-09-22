package org.agbrothers.englishbot.entity;

import javax.persistence.*;

@Entity
@Table
public class Word {
    @Id @GeneratedValue
    @Column
    private Long Id;
    @Column (nullable = false)
    private String englishValue;
    @Column (nullable = false)
    private String ukrainianValue;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
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

    public Word() {}

    public Word(Long id, String englishValue, String ukrainianValue) {
        Id = id;
        this.englishValue = englishValue;
        this.ukrainianValue = ukrainianValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!Id.equals(word.Id)) return false;
        if (!englishValue.equals(word.englishValue)) return false;
        return ukrainianValue.equals(word.ukrainianValue);
    }

    @Override
    public int hashCode() {
        int result = Id.hashCode();
        result = 31 * result + englishValue.hashCode();
        result = 31 * result + ukrainianValue.hashCode();
        return result;
    }
}
