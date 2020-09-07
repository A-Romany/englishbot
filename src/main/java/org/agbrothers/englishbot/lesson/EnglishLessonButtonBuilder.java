package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.menu.MainMenuButtonsBuilder;
import org.agbrothers.englishbot.persistence.Word;
import org.agbrothers.englishbot.process.ButtonsBuilder;

import java.util.*;

import static org.agbrothers.englishbot.constant.ButtonLabel.LESSONS;


public class EnglishLessonButtonBuilder extends ButtonsBuilder {
    private final Lesson lesson;
    public EnglishLessonButtonBuilder(Lesson lesson) {
        this.lesson = lesson;
    }


    @Override
    public Map<String, String> getKeyboardButtons(String messageText) {

        List<Word> answers =lesson.getAnswers(lesson.getCurrentWord());
        if(answers == null){
            MainMenuButtonsBuilder mainMenuButtonsBuilder = new MainMenuButtonsBuilder();
                return  mainMenuButtonsBuilder.getKeyboardButtons(LESSONS);
        }

        Map<String, String> keyboardMap =  new HashMap<>();


        for (Word answer : answers) {
            keyboardMap.put(answer.getWordInUkrainian(), answer.getWordInUkrainian());
        }
        return keyboardMap;
    }
}
