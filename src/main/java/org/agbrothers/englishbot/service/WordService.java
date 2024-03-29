package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordService {

    private final WordRepository wordRepository;
    public static final Integer WORD_POOL_SIZE = 10;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> getRandomWordPool() {
        List<Long> wordIds = wordRepository.getWordIds();
        Collections.shuffle(wordIds);
        List<Long> lessonWordsIds = new ArrayList<>(wordIds.subList(0, WORD_POOL_SIZE));
        return wordRepository.findWordsByIdIn(lessonWordsIds);
    }
}
