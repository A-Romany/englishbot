package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.LessonRepository;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {
    final LessonRepository lessonRepository = mock(LessonRepository.class);
    final WordService wordPoolService = mock(WordService.class);
    final LessonService lessonService = new LessonService(wordPoolService, lessonRepository);

    @Test
    void getLesson_LessonWithCurrentWordExists_ExpectNoLessonCreation() {
        List<Word> words = Collections.singletonList(new Word());
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);
        lesson.setCurrentWord(new Word());

        when(lessonRepository.findLessonByUser(user)).thenReturn(lesson);

        lessonService.getLesson(user);

        verify(wordPoolService, never()).getRandomWordPool();
        verify(lessonRepository, never()).saveAndFlush(any(Lesson.class));
    }

    @Test
    void getLesson_NoLesson_ExpectNewLesson() {
        User user = new User();
        when(lessonRepository.findLessonByUser(user)).thenReturn(null);

        lessonService.getLesson(user);

        verify(wordPoolService).getRandomWordPool();
        verify(lessonRepository).saveAndFlush(any(Lesson.class));
    }

    @Test
    void getLesson_NoCurrentWordInLesson_ExpectNewLesson() {
        List<Word> words = Collections.singletonList(new Word());
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);

        when(lessonRepository.findLessonByUser(user)).thenReturn(lesson);
        when(wordPoolService.getRandomWordPool()).thenReturn(words);

        lessonService.getLesson(user);

        verify(wordPoolService).getRandomWordPool();
        verify(lessonRepository).delete(any(Lesson.class));
        verify(lessonRepository).saveAndFlush(any(Lesson.class));
    }

    @Test
    void createLesson() {
        List<Word> words = Collections.singletonList(new Word());
        User user = new User();

        when(wordPoolService.getRandomWordPool()).thenReturn(words);

        lessonService.createLesson(user);

        verify(wordPoolService).getRandomWordPool();
        verify(lessonRepository).saveAndFlush(any(Lesson.class));
    }

    @Test
    void removeLesson_NoUser_ExpectLessonNotRemoved() {
        List<Word> words = Collections.singletonList(new Word());
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);

        when(lessonRepository.findLessonByUser(user)).thenReturn(null);
        lessonService.removeLesson(lesson, user);

        verify(wordPoolService, never()).getRandomWordPool();
        verify(lessonRepository, never()).saveAndFlush(any(Lesson.class));
        verify(lessonRepository, never()).delete(any(Lesson.class));
    }

    @Test
    void removeLesson_HasUser_ExpectRemoveLesson() {
        List<Word> words = Collections.singletonList(new Word());
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);

        when(lessonRepository.findLessonByUser(user)).thenReturn(lesson);
        lessonService.removeLesson(lesson, user);

        verify(wordPoolService, never()).getRandomWordPool();
        verify(lessonRepository, never()).saveAndFlush(any(Lesson.class));
        verify(lessonRepository).delete(any(Lesson.class));
    }

    @Test
    void getNextWord_HasNextWord_ExpectCurrentWord() {
        List<Word> words = new ArrayList<>();
        words.add(new Word());
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);
        lesson.setCurrentWord(new Word());

        Word result = lessonService.getNextWord(lesson);

        assertFalse(words.contains(result));
        assertNotNull(result);
        assertEquals(lesson.getCurrentWord(), result);
    }

    @Test
    void getNextWord_HasNotNextWord_ExpectNullCurrentWord() {
        List<Word> words = new ArrayList<>();
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);

        Word result = lessonService.getNextWord(lesson);

        assertNull(result);
    }

    @Test
    void getAnswers_NoCurrentWordInLesson_ExpectEmptyList() {
        List<Word> words = new ArrayList<>();
        User user = new User();
        Lesson lesson = new Lesson(words, words, user);

        List<Word> result = lessonService.getAnswers(lesson);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAnswers_HasCurrentWordInLesson_ExpectAnswerList() {
        User user = new User();
        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            words.add(new Word());
        }
        Lesson lesson = new Lesson(words, words, user);
        lesson.setCurrentWord(words.get(0));

        List<Word> result = lessonService.getAnswers(lesson);

        assertNotNull(result);
        assertNotEquals(words, result);
        assertFalse(result.isEmpty());
        assertTrue(words.containsAll(result));
        assertTrue(result.contains(lesson.getCurrentWord()));
    }
}