package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.processor.lesson.LessonProcessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractEnglishLessonProcessor extends LessonProcessor {

    @Override
    public String getCorrectAnswer(Word word) {
        return word.getUkrainianValue();
    }

    @Override
    public List<Map<String, String>> formAnswersMap(List<Word> answers) {
        List<Map<String, String>> keyboardMaps = new ArrayList<>();
        keyboardMaps.add(getKeyboardMapForAnswer(answers));
        return keyboardMaps;
    }

    private Map<String, String> getKeyboardMapForAnswer(List<Word> answers) {
        Map<String, String> keyboardMap = new LinkedHashMap<>();
        answers.forEach(word -> keyboardMap.put(word.getUkrainianValue(), word.getUkrainianValue()));
        return keyboardMap;
    }
}
