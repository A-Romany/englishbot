package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordPoolService {

    private final WordRepository wordRepository;
    public static final Integer COUNT_ANSWER = 10;

    public WordPoolService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> getRandomWordPool() {
        List<Word> allWorld = wordRepository.findAll();
        Collections.shuffle(allWorld);
        return new ArrayList<>(allWorld. subList(0, COUNT_ANSWER));
    }
}
