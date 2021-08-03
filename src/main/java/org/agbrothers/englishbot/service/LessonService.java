package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final WordService wordPoolService;

    public LessonService(WordService wordPoolService, LessonRepository lessonRepository) {
        this.wordPoolService = wordPoolService;
        this.lessonRepository = lessonRepository;
    }

    public Lesson getLesson(User user) {
        if (lessonRepository.findLessonByUser(user) == null) {
            return createLesson(user);
        } else if (lessonRepository.findLessonByUser(user).getCurrentWord() == null) {
            removeLesson(lessonRepository.findLessonByUser(user), user);
            return createLesson(user);
        }
        return lessonRepository.findLessonByUser(user);
    }

    public Lesson createLesson(User user) {
        List<Word> wordPool = wordPoolService.getRandomWordPool();
        List<Word> answersPool = new ArrayList<>(wordPool);
        return lessonRepository.saveAndFlush(new Lesson(wordPool, answersPool, user));
    }

    public void removeLesson(Lesson lesson, User user) {
        if (lessonRepository.findLessonByUser(user) != null) {
            lessonRepository.delete(lesson);
        }
    }

    public Word getNextWord(Lesson lesson) {
        Iterator<Word> iterator = lesson.getWordPool().iterator();
        if (iterator.hasNext()) {
            lesson.setCurrentWord(iterator.next());
            iterator.remove();
        } else {
            lesson.setCurrentWord(null);
        }
        return lesson.getCurrentWord();
    }

    public List<Word> getAnswers(Lesson lesson) {
        Word currentWord = lesson.getCurrentWord();
        if (currentWord == null) {
            return Collections.emptyList();
        }

        List<Word> answersPool = lesson.getAnswersPool();
        Collections.shuffle(answersPool, new SecureRandom());

        List<Word> answers = answersPool.stream()
                .filter(word -> !(currentWord.equals(word)))
                .limit(4)
                .collect(Collectors.toList());
        answers.add(currentWord);

        Collections.shuffle(answers, new SecureRandom());
        return answers;
    }
}