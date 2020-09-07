package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.persistence.Word;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class EnglishWordPoolService {
    private List<Word> wordList;
    public static final Integer COUNT_ANSWER = 10;
    public EnglishWordPoolService() {
        initHardcodedWordsRepository();
    }

    public void initHardcodedWordsRepository() {

        wordList = Arrays.asList(new Word(1, "black", "чорний"),
        new Word(2, "grey", "сірий"),
        new Word(3, "green", "зелений"),
        new Word(4, "white", "білий"),
        new Word(5, "brown", "коричневий"),
        new Word(6, "blue", "синій"),
        new Word(7, "red", "червоний"),
        new Word(8, "yellow", "жовтий"),
        new Word(9, "pink", "рожевий"),
        new Word(10, "orange", "жовтогарячий"),
        new Word(11, "purple", "фіолетовий"),
        new Word(12, "beige", "бежевий"));
    }

    public List<Word> getRandomWordPool() {
        Collections.shuffle(wordList, new SecureRandom());
        List<Word> words = wordList.subList(0, COUNT_ANSWER);
        return new ArrayList<>(words);
    }
}
