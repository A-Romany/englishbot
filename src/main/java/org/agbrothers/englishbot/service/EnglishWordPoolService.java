package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.persistence.Word;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnglishWordPoolService {
    private List<Word> wordList = new ArrayList<>();
    public static final Integer COUNT_ANSWER = 10;
    public EnglishWordPoolService() {
        initHardcodedWordsRepository();
    }

    public void initHardcodedWordsRepository() {
        Word black = new Word(1, "black", "чорний");
        Word grey = new Word(2, "grey", "сірий");
        Word green = new Word(3, "green", "зелений");
        Word white = new Word(4, "white", "білий");
        Word brown = new Word(5, "brown", "коричневий");
        Word blue = new Word(6, "blue", "синій");
        Word red = new Word(7, "red", "червоний");
        Word yellow = new Word(8, "yellow", "жовтий");
        Word pink = new Word(9, "pink", "рожевий");
        Word orange = new Word(10, "orange", "жовтогарячий");
        Word purple = new Word(11, "purple", "фіолетовий");
        Word beige = new Word(12, "beige", "бежевий");

        wordList.add(black);
        wordList.add(grey);
        wordList.add(green);
        wordList.add(white);
        wordList.add(brown);
        wordList.add(blue);
        wordList.add(red);
        wordList.add(yellow);
        wordList.add(pink);
        wordList.add(orange);
        wordList.add(purple);
        wordList.add(beige);
    }

    public Set<Word> getRandomWordPool() {
        Collections.shuffle(wordList);
        List<Word> words = wordList.subList(0, COUNT_ANSWER);
        return new HashSet<>(words);
    }
}
