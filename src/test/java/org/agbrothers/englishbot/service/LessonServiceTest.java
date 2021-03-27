package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {
    final WordPoolService wordPoolService = mock(WordPoolService.class);
    final LessonService lessonService = new LessonService(wordPoolService);

    @Test
    void getLesson_LessonWithCurrentWordExists_ExpectNoLessonCreation() {
        List<Word> words = Collections.singletonList(new Word());
        Lesson lesson = new Lesson(words, words);
        lesson.setCurrentWord(new Word());
        String chatId = "testChatId";
        Map<String, Lesson> lessonRegistry = new HashMap<>();
        lessonRegistry.put(chatId, lesson);
        lessonService.setLessonRegistry(lessonRegistry);

        Lesson result = lessonService.getLesson(chatId);

        assertEquals(lesson, result);
        verify(wordPoolService, never()).getRandomWordPool();
    }

    @Test
    void getLesson_NoLesson_ExpectNewLesson() {
        String chatId = "testChatId";
        Map<String, Lesson> lessonRegistry = new HashMap<>();
        lessonService.setLessonRegistry(lessonRegistry);
        List<Word> words = Collections.singletonList(new Word());

        when(wordPoolService.getRandomWordPool()).thenReturn(words);

        Lesson result = lessonService.getLesson(chatId);

        assertNotNull(result);
        assertEquals(result, lessonRegistry.get(chatId));
    }

    @Test
    void getLesson_NoCurrentWordInLesson_ExpectNewLesson() {
        List<Word> words = Collections.singletonList(new Word());
        Lesson lesson = new Lesson(words, words);
        String chatId = "testChatId";
        Map<String, Lesson> lessonRegistry = new HashMap<>();
        lessonRegistry.put(chatId, lesson);
        lessonService.setLessonRegistry(lessonRegistry);

        when(wordPoolService.getRandomWordPool()).thenReturn(words);

        Lesson result = lessonService.getLesson(chatId);

        assertNotNull(result);
        assertNotEquals(lesson, result);
        assertEquals(lessonRegistry.get(chatId), result);
        assertNotEquals(lessonRegistry.get(chatId), lesson);
        verify(wordPoolService).getRandomWordPool();
    }

    @Test
    void createLesson() {
        List<Word> words = Collections.singletonList(new Word());

        when(wordPoolService.getRandomWordPool()).thenReturn(words);

        Lesson lesson = lessonService.createLesson();

        verify(wordPoolService).getRandomWordPool();
        assertEquals(lesson.getAnswersPool(), words);
        assertEquals(lesson.getWordPool(), words);
    }

    @Test
    void removeLesson() {
        String chatId = "testChatId";
        Map<String, Lesson> lessonRegistry = new HashMap<>();
        lessonService.setLessonRegistry(lessonRegistry);
        Lesson lesson = new Lesson(Collections.emptyList(), Collections.emptyList());
        lessonRegistry.put(chatId, lesson);

        lessonRegistry.remove(chatId);

        assertNull(lessonRegistry.get(chatId));
    }

    @Test
    void getNextWord_HasNextWord_ExpectCurrentWord() {
        List<Word> words = new ArrayList<>();
        words.add(new Word());
        Lesson lesson = new Lesson(words, words);
        lesson.setCurrentWord(new Word());
        String chatId = "testChatId";
        Map<String, Lesson> lessonRegistry = new HashMap<>();
        lessonRegistry.put(chatId, lesson);
        lessonService.setLessonRegistry(lessonRegistry);

        Word result = lessonService.getNextWord(lesson);

        assertFalse(words.contains(result));
        assertNotNull(result);
        assertEquals(lesson.getCurrentWord(), result);
    }

    @Test
    void getNextWord_HasNotNextWord_ExpectNullCurrentWord() {
        List<Word> words = new ArrayList<>();
        Lesson lesson = new Lesson(words, words);
        String chatId = "testChatId";
        Map<String, Lesson> lessonRegistry = new HashMap<>();
        lessonRegistry.put(chatId, lesson);
        lessonService.setLessonRegistry(lessonRegistry);

        Word result = lessonService.getNextWord(lesson);

        assertNull(result);
    }

    @Test
    void getAnswers_NoCurrentWordInLesson_ExpectEmptyList() {
        List<Word> words = new ArrayList<>();
        Lesson lesson = new Lesson(words, words);

        List<Word> result = lessonService.getAnswers(lesson);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAnswers_HasCurrentWordInLesson_ExpectAnswerList() {
        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            words.add(new Word());
        }
        Lesson lesson = new Lesson(words, words);
        lesson.setCurrentWord(words.get(0));

        List<Word> result = lessonService.getAnswers(lesson);

        assertNotNull(result);
        assertNotEquals(words, result);
        assertFalse(result.isEmpty());
        assertTrue(words.containsAll(result));
        assertTrue(result.contains(lesson.getCurrentWord()));
    }
}