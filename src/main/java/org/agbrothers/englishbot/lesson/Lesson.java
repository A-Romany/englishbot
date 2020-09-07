package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.persistence.Word;

import java.security.SecureRandom;
import java.util.*;

public class Lesson {

    private final List<Word> wordPool;//word in Lesson
    private final List<Word> answersPool;
    private int countCorrectAnswers =0;
    private int countIncorrectAnswer=0;
    private Word currentWord;

    public Word getCurrentWord() {
        return currentWord;
    }

    public Lesson(List<Word> wordPool, List<Word> answersPool) {
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

    public List<Word> getAnswers(Word currentWord) {
        if(currentWord == null){
            return null;
        }
        List<Word> answers = new ArrayList<>();

        answers.add(currentWord);
        Collections.shuffle(answersPool, new SecureRandom());
        for(int i=0;answers.size()<5;i+=2){
            if(answersPool.get(i).equals(currentWord)){
                System.out.println("Повтор");
            }
            else {
                answers.add(answersPool.get(i));
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
