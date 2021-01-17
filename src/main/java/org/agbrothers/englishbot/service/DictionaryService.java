package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService {
    private final WordRepository wordRepository;

    public DictionaryService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public WordRepository getWordRepository() {
        return wordRepository;
    }

    public void addWord(Word word){
        wordRepository.saveAndFlush(word);
    }

    public Word getWordFromRepository(String string){
        return wordRepository.findByEnglishValue(string);
    }
}
