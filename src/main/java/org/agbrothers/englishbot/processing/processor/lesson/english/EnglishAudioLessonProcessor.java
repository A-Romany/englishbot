package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@Component
public class EnglishAudioLessonProcessor extends AbstractEnglishLessonProcessor {

    private final String voicerssApi = "http://api.voicerss.org/?key=60e35db111144fcda2af3958169f6f11&hl=en-us&src=";
    private RestTemplate restTemplate;

    @Override
    public String getMessageToTranslate(Word word) {
        return "Прослухайте аудіофайл і виберіть правильну відповідність:";
    }

    @Override
    public void process(ProcessingExchange exchange) {
        super.process(exchange);
        Lesson lesson = getLessonService().getLesson(exchange.getUser());
        if (lesson.getCurrentWord()!=null) {
            InputFile inputFile = new InputFile(getAudioFile(lesson.getCurrentWord().getEnglishValue()), "audio");
            exchange.setAudio(inputFile);
        }
    }

    private InputStream getAudioFile(String word) {
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(voicerssApi +word, byte[].class);
        return new ByteArrayInputStream(Objects.requireNonNull(entity.getBody()));
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
