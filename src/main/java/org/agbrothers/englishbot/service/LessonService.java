package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.lesson.Lesson;
import org.agbrothers.englishbot.persistence.Word;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LessonService {

    private final Map<String, Lesson> lessonRegistry;
    private final EnglishWordPoolService englishWordPoolService;

    public LessonService(EnglishWordPoolService englishWordPoolService) {
        this.englishWordPoolService = englishWordPoolService;
        this.lessonRegistry = new HashMap<>();
    }

    //TODO getLesson(String chatId) from lessonRegistry
    public Lesson getLesson (String chatId){
        if(!lessonRegistry.containsKey(chatId)){
            Lesson lesson = createLesson();
            lessonRegistry.put(chatId,lesson);
        }
        return lessonRegistry.get(chatId);
    }

    public Lesson createLesson(){
        Set<Word> wordPool = englishWordPoolService.getRandomWordPool();
        List<Word> answersPool = new ArrayList<>(wordPool);
        return new Lesson(wordPool, answersPool);
    }

}
