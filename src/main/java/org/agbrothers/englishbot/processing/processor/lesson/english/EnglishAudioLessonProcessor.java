package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.ResponseMessage;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.service.texttospeech.TextToSpeechClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static org.agbrothers.englishbot.constant.CommonPhrase.LISTEN_TO_AUDIO;
import static org.agbrothers.englishbot.constant.CommonPhrase.MAKE_CHOICE;

@Component
public class EnglishAudioLessonProcessor extends AbstractEnglishLessonProcessor {


    private TextToSpeechClient voicerssTextToSpeechClient;

    @Override
    public String getValueToTranslate(Word word) {
        return ""; //TODO: EN-67 (appendResponseMessageText)
    }

    @Override
    public void process(ProcessingExchange exchange) {
        Lesson lesson = getLessonService().getLesson(exchange.getUser());

        addTextMessage(exchange, lesson);

        if (lesson.getCurrentWord() != null) {
            exchange.appendResponseMessageText(LISTEN_TO_AUDIO); //TODO: EN-67 (appendResponseMessageText)
            addResponseWithAudio(exchange, lesson);
            addTextMessageWithButtons(exchange, lesson);
        }

        exchange.setExchangeState(State.READY_TO_SEND);
    }

    private void addTextMessage(ProcessingExchange exchange, Lesson lesson) {
        ResponseMessage responseMessage = new ResponseMessage();
        String responseMessageText = getResponseMessageText(exchange.getMessageText(), lesson);

        responseMessage.setResponseMessageText(responseMessageText);
        if (lesson.getCurrentWord() == null) {
            responseMessage.setResponseButtons(getKeyboardButtons(lesson));
        }
        exchange.getResponseMessages().add(responseMessage);
    }

    private void addResponseWithAudio(ProcessingExchange exchange, Lesson lesson) {
        InputFile inputFile = new InputFile(voicerssTextToSpeechClient.getAudioFile(lesson.getCurrentWord().getEnglishValue()), "audio");

        ResponseMessage audioResponseMessage =  new ResponseMessage();
        audioResponseMessage.setAudio(inputFile);

        exchange.getResponseMessages().add(audioResponseMessage);
    }

    private void addTextMessageWithButtons(ProcessingExchange exchange, Lesson lesson) {
        ResponseMessage responseMessageWithButtons =  new ResponseMessage();

        responseMessageWithButtons.setResponseMessageText(MAKE_CHOICE);
        responseMessageWithButtons.setResponseButtons(getKeyboardButtons(lesson));

        exchange.getResponseMessages().add(responseMessageWithButtons);
    }


    @Autowired
    public void setVoicerssTextToSpeechClient(TextToSpeechClient voicerssTextToSpeechClient) {
        this.voicerssTextToSpeechClient = voicerssTextToSpeechClient;
    }

    public TextToSpeechClient getVoicerssTextToSpeechClient() {
        return voicerssTextToSpeechClient;
    }
}
