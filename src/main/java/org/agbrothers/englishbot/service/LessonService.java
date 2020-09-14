package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
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


    public Lesson getLesson (String chatId){
        if((!lessonRegistry.containsKey(chatId))||
                (lessonRegistry.get(chatId).getCurrentWord()==null)){
            Lesson lesson = createLesson();
            lessonRegistry.put(chatId,lesson);
        }
        return lessonRegistry.get(chatId);
    }

    public Lesson createLesson(){
        List<Word> wordPool = englishWordPoolService.getRandomWordPool();
        List<Word> answersPool = new ArrayList<>(wordPool);
        return new Lesson(wordPool, answersPool);
    }
}
