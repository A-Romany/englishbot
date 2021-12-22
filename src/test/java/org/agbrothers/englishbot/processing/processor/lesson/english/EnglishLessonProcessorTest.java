package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.entity.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnglishLessonProcessorTest {

    @Test
    void getMessageToTranslate() {
        EnglishLessonProcessor englishLessonProcessor = new EnglishLessonProcessor();
        Word word = new Word("word", "слово");

        String result = englishLessonProcessor.getValueToTranslate(word);

        assertEquals("word", result);
    }
}