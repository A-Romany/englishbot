package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.entity.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnglishAudioLessonProcessorTest {

    @Test
    void getValueToTranslate() {
        EnglishAudioLessonProcessor englishAudioLessonProcessor = new EnglishAudioLessonProcessor();
        Word word = new Word("word", "слово");

        String result = englishAudioLessonProcessor.getValueToTranslate(word);

        assertEquals("", result);

    }

    @Test
    void process() {
    }

    @Test
    void setRestTemplate() {
    }
}