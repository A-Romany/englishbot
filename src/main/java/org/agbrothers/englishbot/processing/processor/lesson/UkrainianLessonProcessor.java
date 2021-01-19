package org.agbrothers.englishbot.processing.processor.lesson;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class UkrainianLessonProcessor extends LessonProcessor {

    @Override
    protected String getCorrectAnswer(Word word) {
        return word.getEnglishValue();
    }

    @Override
    protected String getValueToTranslate(Word word) {
        return word.getUkrainianValue();
    }

    @Override
    protected Map<String, String> formAnswersMap(List<Word> answers) {
        Map<String, String> keyboardMap =  new HashMap<>();
        for (Word answer : answers) {
            keyboardMap.put(answer.getEnglishValue(), answer.getEnglishValue());
        }
        return keyboardMap;
    }
}
