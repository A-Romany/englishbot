package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.persistence.Word;

import java.util.*;

public class Lesson {

    private final Set<Word> wordPool;//word in Lesson
    private final List<Word> answersPool;
    private int countCorrectAnswers =0;
    private int countIncorrectAnswer=0;



    public Word getCurrentWord() {
        return currentWord;
    }

    private Word currentWord;

    public Lesson(Set<Word> wordPool, List<Word> answersPool) {
        this.wordPool = wordPool;
        this.answersPool = answersPool;
    }


    public Word getNextWord() {
        Iterator<Word> iterator = wordPool.iterator();
        if (iterator.hasNext()) {
            currentWord = iterator.next();
            iterator.remove();
        } else {
            currentWord = null;
        }
        return currentWord;
    }

    public Set<Word> getAnswers(Word currentWord) {
        if(currentWord == null){
            return null;
        }
        Set<Word> answers = new HashSet<>();

        answers.add(currentWord);
        Collections.shuffle(answersPool);

        Iterator<Word> iterator = answersPool.iterator();
        while (answers.size()<5){
            if(iterator.hasNext()){
                answers.add(iterator.next());
            } else {
                break;
            }
        }
        return answers;
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
}
