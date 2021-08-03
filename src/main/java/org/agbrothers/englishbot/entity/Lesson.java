package org.agbrothers.englishbot.entity;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Lesson {

    @Id
    @GeneratedValue
    @Column
    private Long Id;

    @ManyToMany
    @JoinTable(name = "lesson_word_pool",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "word_pool_id"))
    private List<Word> wordPool = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "lesson_answers_pool",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_pool_id"))
    private List<Word> answersPool = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column
    private int countCorrectAnswers = 0;

    @Column
    private int countIncorrectAnswer = 0;

    @OneToOne
    @JoinColumn(name = "current_word_id")
    private Word currentWord;

    public Lesson() {
    }

    public Lesson(List<Word> wordPool, List<Word> answersPool, User user) {
        this.wordPool = wordPool;
        this.answersPool = answersPool;
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Word> getWordPool() {
        return wordPool;
    }

    public List<Word> getAnswersPool() {
        return answersPool;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public void setCountCorrectAnswers(int countCorrectAnswers) {
        this.countCorrectAnswers = countCorrectAnswers;
    }

    public int getCountIncorrectAnswer() {
        return countIncorrectAnswer;
    }

    public void setCountIncorrectAnswer(int countIncorrectAnswer) {
        this.countIncorrectAnswer = countIncorrectAnswer;
    }

    public void setCurrentWord(Word currentWord) {
        this.currentWord = currentWord;
    }
}