package org.agbrothers.englishbot.persistence;


public class Word {

    private Integer Id;
    private String englishValue;
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
