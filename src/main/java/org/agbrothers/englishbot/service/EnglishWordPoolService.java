package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EnglishWordPoolService {

    private WordRepository wordRepository;
    private List<Word> wordList;
    public static final Integer COUNT_ANSWER = 10;

    public EnglishWordPoolService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        initHardcodedWordsRepository();
    }

    public void initHardcodedWordsRepository() {
        wordList = Arrays.asList(new Word(1L, "black", "чорний"),
        new Word(2L, "grey", "сірий"),
        new Word(3L, "green", "зелений"),
        new Word(4L, "white", "білий"),
        new Word(5L, "brown", "коричневий"),
        new Word(6L, "blue", "синій"),
        new Word(7L, "red", "червоний"),
        new Word(8L, "yellow", "жовтий"),
        new Word(9L, "pink", "рожевий"),
        new Word(10L, "orange", "жовтогарячий"),
        new Word(11L, "purple", "фіолетовий"),
        new Word(12L, "beige", "бежевий"));
    }

    public List<Word> getRandomWordPool() {
        Collections.shuffle(wordList, new SecureRandom());
        return new ArrayList<>(wordList.subList(0, COUNT_ANSWER));
    }

}
