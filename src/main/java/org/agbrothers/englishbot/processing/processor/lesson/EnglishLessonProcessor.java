package org.agbrothers.englishbot.processing.processor.lesson;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class EnglishLessonProcessor extends LessonProcessor {

    @Override
    protected String getCorrectAnswer(Word word) {
        return word.getUkrainianValue();
    }

    @Override
    protected String getValueToTranslate(Word word) {
        return word.getEnglishValue();
    }

    @Override
    protected Map<String, String> formAnswersMap(List<Word> answers) {
        Map<String, String> keyboardMap =  new HashMap<>();
        for (Word answer : answers) {
            keyboardMap.put(answer.getUkrainianValue(), answer.getUkrainianValue());
        }
        return keyboardMap;
    }
}
