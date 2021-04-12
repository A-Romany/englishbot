package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

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
        if(lessonRepository.findLessonByUser(user) != null) {
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
        if (lesson.getCurrentWord() == null) {
            return Collections.emptyList();
        }
        List<Word> answers = new ArrayList<>();
        answers.add(lesson.getCurrentWord());

        List<Word> answersPool = lesson.getAnswersPool();
        Collections.shuffle(answersPool, new SecureRandom());
        for (int i = 0; answers.size() < 5; i++) {
            if (!answersPool.get(i).equals(lesson.getCurrentWord())) {
                answers.add(answersPool.get(i));
            }
        }
        Collections.shuffle(answers, new SecureRandom());
        return answers;
    }
}