package org.agbrothers.englishbot.buttonsbuilder;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import java.util.*;
import static org.agbrothers.englishbot.constant.ButtonLabel.LESSONS;

public class UkrainianLessonButtonBuilder extends ButtonsBuilder {
    private final Lesson lesson;
    public UkrainianLessonButtonBuilder(Lesson lesson) {
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
            keyboardMap.put(answer.getEnglishValue(), answer.getEnglishValue());
        }
        return keyboardMap;
    }
}
