package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.ResponseMessage;
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

import static org.agbrothers.englishbot.constant.CommonPhrase.MAKE_CHOICE_AUDIO;

@Component
public class EnglishAudioLessonProcessor extends AbstractEnglishLessonProcessor {

    private RestTemplate restTemplate;

    @Override
    public String getValueToTranslate(Word word) {
        return "";
    }


    @Override
    public void process(ProcessingExchange exchange) {
        Lesson lesson = getLessonService().getLesson(exchange.getUser());
        String responseMessageText = getResponseMessageText(exchange.getMessageText(), lesson);

        exchange.getResponseMessageList().add(0, new ResponseMessage());
        exchange.getResponseMessageList().get(0).setResponseMessageText(responseMessageText);

        if (lesson.getCurrentWord() == null) {
            exchange.getResponseMessageList().get(0).setResponseButtons(getKeyboardButtons(lesson));
        } else {
            exchange.appendResponseMessageText(MAKE_CHOICE_AUDIO);
            InputFile inputFile = new InputFile(getAudioFile(lesson.getCurrentWord().getEnglishValue()), "audio");

            exchange.getResponseMessageList().add(1, new ResponseMessage());
            exchange.getResponseMessageList().get(1).setAudio(inputFile);
            exchange.getResponseMessageList().add(2, new ResponseMessage());
            exchange.getResponseMessageList().get(2).setResponseMessageText("Виберіть правильну відповідь:");
            exchange.getResponseMessageList().get(2).setResponseButtons(getKeyboardButtons(lesson));
        }
        exchange.setExchangeState(State.READY_TO_SEND);
    }

    private InputStream getAudioFile(String word) {
        String voicerssApi = "http://api.voicerss.org/?key=60e35db111144fcda2af3958169f6f11&hl=en-us&src=";
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(voicerssApi +word, byte[].class);
        return new ByteArrayInputStream(Objects.requireNonNull(entity.getBody()));
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
