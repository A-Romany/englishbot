package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.entity.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AbstractEnglishLessonProcessorTest {

    private final AbstractEnglishLessonProcessor abstractEnglishLessonProcessor = Mockito.mock(AbstractEnglishLessonProcessor.class);

    @BeforeEach
    public void setUp() {
        Mockito.doCallRealMethod()
                .when(abstractEnglishLessonProcessor)
                .getCorrectAnswer(any(Word.class));

        Mockito.doCallRealMethod()
                .when(abstractEnglishLessonProcessor)
                .formAnswersMap(any());
    }

    @Test
    void getCorrectAnswer() {
        Word word = new Word("word", "слово");

        String correctAnswer = abstractEnglishLessonProcessor.getCorrectAnswer(word);

        assertEquals("слово", correctAnswer);
    }

    @Test
    void formAnswersMap() {
        List<Word> answers = new ArrayList<>();
        answers.add(new Word("word", "слово"));
        answers.add(new Word("world", "світ"));
        answers.add(new Word("work", "робота"));

        Map<String, String> keyboardMap = new LinkedHashMap<>();
        answers.forEach(word -> keyboardMap.put(word.getUkrainianValue(), word.getUkrainianValue()));
        List<Map<String, String>> keyboardMaps = new ArrayList<>();
        keyboardMaps.add(keyboardMap);


        List<Map<String, String>> answersMap = abstractEnglishLessonProcessor.formAnswersMap(answers);

        assertEquals( keyboardMaps, answersMap);
    }
}
