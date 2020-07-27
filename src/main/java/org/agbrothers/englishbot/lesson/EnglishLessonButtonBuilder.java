package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.persistence.Word;
import org.agbrothers.englishbot.process.ButtonsBuilder;

import java.util.*;


public class EnglishLessonButtonBuilder extends ButtonsBuilder {
    private final Lesson lesson;
    public EnglishLessonButtonBuilder(Lesson lesson) {
        this.lesson = lesson;
    }


    @Override
    public Map<String, String> getKeyboardButtons(String messageText) {
        Set<Word> answers =lesson.getAnswers(lesson.getCurrentWord());
        if(answers == null){
            return null;
        }
        Map<String, String> keyboardMap =  new HashMap<>();
        Iterator<Word> iterator = answers.iterator();
        for (int i = 0; i < answers.size(); i++) {
            if (iterator.hasNext()) {
                Word nextWord = iterator.next();
                keyboardMap.put(nextWord.getWordInUkrainian(), nextWord.getWordInUkrainian());
                //iterator.remove(); why 3
            }
        }
        return keyboardMap;
    }
}
