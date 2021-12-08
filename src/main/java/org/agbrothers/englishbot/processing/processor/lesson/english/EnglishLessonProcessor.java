package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.stereotype.Component;


@Component
public class EnglishLessonProcessor extends AbstractEnglishLessonProcessor {

    @Override
    protected String getMessageToTranslate(Word word) {
        return word.getEnglishValue();
    }

}
