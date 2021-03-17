package org.agbrothers.englishbot.entity;

import javax.persistence.*;

@Entity
@Table
public class Word {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column(nullable = false, unique = true)
    private String englishValue;
    @Column(nullable = false)
    private String ukrainianValue;

    public Word() {
    }

    public Word(String englishValue, String ukrainianValue) {
        this.englishValue = englishValue;
        this.ukrainianValue = ukrainianValue;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    public String getUkrainianValue() {
        return ukrainianValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (id == null || !id.equals(word.id)) return false;
        if (!englishValue.equals(word.englishValue)) return false;
        return ukrainianValue.equals(word.ukrainianValue);
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (id != null) {
            result = id.hashCode();
        }
        result = 31 * result + englishValue.hashCode();
        result = 31 * result + ukrainianValue.hashCode();
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
