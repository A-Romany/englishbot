package service;

import persistence.Word;

import java.util.*;

public class WordPoolService {
    private List<Word> wordList = new ArrayList<>();

    public WordPoolService() {
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

    public void wordPoolListEnglishLesson (List<Word> wordList) {

        int limit = wordList.size();
        Map<Integer, Word> englishLessons = new HashMap<>();
        int[] wordPoolListForEnglishLesson = new int[5];

        Random rnd = new Random();
        int check;
        boolean tmp = false;
        for (int i = 0; i < 5; i++) {
            check = rnd.nextInt(limit);
            for (int j = 0; j < i; j++) {
                if (wordPoolListForEnglishLesson[j] == check) {
                    i--;
                    tmp = true;
                    break;
                }
            }
            if (!tmp) {
                wordPoolListForEnglishLesson[i] = check;

            }
            tmp = false;
        }

        for (int i = 0; i < 5; i++) {

            englishLessons.put(i, wordList.get(wordPoolListForEnglishLesson[i]));
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(englishLessons.get(i).getEnglishValue() + "  " + englishLessons.get(i).getId());
        }
    }

    public Set<Word> getRandomWordPool() {
        Collections.shuffle(wordList);
        List<Word> words = wordList.subList(0,5);
        return new HashSet<>(words);
    }
}
