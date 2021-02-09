package org.agbrothers.englishbot.entity;

import java.util.List;

public class Lesson {

    private final List<Word> wordPool;//word in Lesson
    private final List<Word> answersPool;
    private int countCorrectAnswers = 0;
    private int countIncorrectAnswer = 0;
    private Word currentWord;

    public Lesson(List<Word> wordPool, List<Word> answersPool) {
        this.wordPool = wordPool;
        this.answersPool = answersPool;
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