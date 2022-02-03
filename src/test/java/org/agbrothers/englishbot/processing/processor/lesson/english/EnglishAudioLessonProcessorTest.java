package org.agbrothers.englishbot.processing.processor.lesson.english;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.service.LessonService;
import org.agbrothers.englishbot.service.texttospeech.TextToSpeechClient;
import org.agbrothers.englishbot.service.texttospeech.VoicerssTextToSpeechClient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EnglishAudioLessonProcessorTest {
    final LessonService lessonService = mock(LessonService.class);
    final VoicerssTextToSpeechClient voicerssTextToSpeechClient = mock(VoicerssTextToSpeechClient.class);

    EnglishAudioLessonProcessor englishAudioLessonProcessor = new EnglishAudioLessonProcessor();

    @Test
    void getValueToTranslate() {
        Word word = new Word("word", "слово");

        String result = englishAudioLessonProcessor.getValueToTranslate(word);

        assertEquals("", result);
    }

    @Test
    void process_NoCurrentWord_ExpectOnlyTextMessage() {
        User user = new User();
        List<Word> words = new ArrayList<>();
        Lesson lesson = new Lesson(words, words, user);
        user.setStateId("English_audio_lesson");
        user.setLesson(lesson);
        ProcessingExchange exchange = new ProcessingExchange(user, "TestMessage", "Test");

        englishAudioLessonProcessor.setVoicerssTextToSpeechClient(voicerssTextToSpeechClient);
        englishAudioLessonProcessor.setLessonService(lessonService);
        when(lessonService.getLesson(exchange.getUser())).thenReturn(lesson);
        assertEquals(0, exchange.getResponseMessages().size());

        englishAudioLessonProcessor.process(exchange);

        assertEquals(State.READY_TO_SEND, exchange.getExchangeState());
        assertEquals(1, exchange.getResponseMessages().size());
    }

    @Test
    void process_CurrentWordExists_ExpectThreeResponseMessage() {
        List<Word> words = new ArrayList<>();

        User user = new User();
        Lesson lesson = new Lesson(words, words, user);
        lesson.setCurrentWord(new Word("Test", "тест"));
        ProcessingExchange exchange = new ProcessingExchange(user, "TestMessage", "Test");
        byte [] fileBytes = new byte[1];
        InputStream inputStream = new ByteArrayInputStream(fileBytes);

        englishAudioLessonProcessor.setVoicerssTextToSpeechClient(voicerssTextToSpeechClient);
        englishAudioLessonProcessor.setLessonService(lessonService);
        when(lessonService.getLesson(exchange.getUser())).thenReturn(lesson);

        assertEquals(0, exchange.getResponseMessages().size());

        englishAudioLessonProcessor.process(exchange);

        assertEquals(State.READY_TO_SEND, exchange.getExchangeState());
        assertEquals(3, exchange.getResponseMessages().size());

        when(voicerssTextToSpeechClient.getAudioFile(anyString())).thenReturn(inputStream);

        verify(voicerssTextToSpeechClient).getAudioFile(anyString());

        assertEquals(3L, exchange.getResponseMessages().stream()
                .filter(responseMessage -> responseMessage.getResponseMessageText() != null)
                .count());

        assertEquals(1L, exchange.getResponseMessages().stream()
                .filter(responseMessage -> responseMessage.getAudio() != null)
                .count());
        assertNotNull(exchange.getResponseMessages().get(1).getAudio());
        assertEquals("", exchange.getResponseMessages().get(1).getResponseMessageText());


        assertEquals(CommonPhrase.MAKE_CHOICE, exchange.getResponseMessages().get(2).getResponseMessageText());
        assertEquals(1L, exchange.getResponseMessages().stream()
                .filter(responseMessage -> responseMessage.getResponseButtons() !=null)
                .count());
        assertNotNull(exchange.getResponseMessages().get(2).getResponseButtons());
    }

    @Test
    void setVoicerssTextToSpeechClient() {
        englishAudioLessonProcessor.setVoicerssTextToSpeechClient(voicerssTextToSpeechClient);

        assertEquals(voicerssTextToSpeechClient, englishAudioLessonProcessor.getVoicerssTextToSpeechClient());
    }

    @Test
    void getVoicerssTextToSpeechClient() {

        englishAudioLessonProcessor.setVoicerssTextToSpeechClient(voicerssTextToSpeechClient);

        TextToSpeechClient result = englishAudioLessonProcessor.getVoicerssTextToSpeechClient();

        assertEquals(voicerssTextToSpeechClient, result);
    }
}