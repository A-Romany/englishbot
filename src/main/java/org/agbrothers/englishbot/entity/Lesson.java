package org.agbrothers.englishbot.entity;

import java.util.List;

public class Lesson {

    private final List<Word> wordPool;//word in Lesson
    private final List<Word> answersPool;

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
}