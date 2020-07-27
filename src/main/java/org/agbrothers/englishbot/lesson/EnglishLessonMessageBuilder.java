package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.persistence.Word;
import org.agbrothers.englishbot.process.MessageBuilder;
import org.agbrothers.englishbot.service.EnglishWordPoolService;

import java.util.Iterator;

import static org.agbrothers.englishbot.constant.ButtonLabel.ENGLISH;

public class EnglishLessonMessageBuilder extends MessageBuilder {

    private final Lesson lesson;


    public EnglishLessonMessageBuilder(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public String getResponseMessageText(String messageText) {//FIXME NullPointerException


        Word wordQuestion = lesson.getNextWord();
        if(wordQuestion==null) {
            return "Урок закінчено. Зачекайте формуються Ваші результати"+ RETURN_MAIN_MENU;
        }
        else {
            return wordQuestion.getEnglishValue();
        }

    }
}
