package org.agbrothers.englishbot.entity;

import javax.persistence.*;

@Entity
@Table
public class Word {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (id == null || word.getId() == null) return false;
        if (!id.equals(word.id)) return false;
        if (!englishValue.equals(word.englishValue)) return false;
        return ukrainianValue.equals(word.ukrainianValue);
    }

    @Override
    public int hashCode() {
        int result = 31;
        if(id != null) {
            result = 31 * id.hashCode();
        }
        if(englishValue != null) {
            result = 31 * result + englishValue.hashCode();
        }
        if(ukrainianValue != null) {
            result = 31 * result + ukrainianValue.hashCode();
        }
        return result;
    }
}
