package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonService {

    private final Map<String, Lesson> lessonRegistry;
    private final WordPoolService wordPoolService;

    public LessonService(WordPoolService wordPoolService) {
        this.wordPoolService = wordPoolService;
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
        List<Word> wordPool = wordPoolService.getRandomWordPool();
        List<Word> answersPool = new ArrayList<>(wordPool);
        return new Lesson(wordPool, answersPool);
    }

    public void removeLesson(String chatId){
        lessonRegistry.remove(chatId);
    }
}
