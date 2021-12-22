package org.agbrothers.englishbot.processing.processor.lesson;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    protected List<Map<String, String>> formAnswersMap(List<Word> answers) {
        List<Map<String, String>> keyboardMaps = new ArrayList<>();
        Map<String, String> keyboardMap = new LinkedHashMap<>();

        answers.forEach(wrd -> keyboardMap.put(wrd.getEnglishValue(), wrd.getEnglishValue()));

        keyboardMaps.add(keyboardMap);
        return keyboardMaps;
    }
}
