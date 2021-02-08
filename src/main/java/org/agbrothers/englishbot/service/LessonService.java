package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class LessonService {

    private final Map<String, Lesson> lessonRegistry;
    private final WordPoolService wordPoolService;


    public LessonService(WordPoolService wordPoolService) {
        this.wordPoolService = wordPoolService;
        this.lessonRegistry = new HashMap<>();
    }

    public Lesson getLesson(String chatId) {
        if ((!lessonRegistry.containsKey(chatId)) ||
                (lessonRegistry.get(chatId).getCurrentWord() == null)) {
            Lesson lesson = createLesson();
            lessonRegistry.put(chatId, lesson);
        }
        return lessonRegistry.get(chatId);
    }

    public Lesson createLesson() {
        List<Word> wordPool = wordPoolService.getRandomWordPool();
        List<Word> answersPool = new ArrayList<>(wordPool);
        return new Lesson(wordPool, answersPool);
    }

    public void removeLesson(String chatId) {
        lessonRegistry.remove(chatId);
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

    public List<Word> getAnswers(Word currentWord, Lesson lesson) {
        if (currentWord == null) {
            return Collections.emptyList();
        }
        List<Word> answers = new ArrayList<>();
        answers.add(currentWord);


        List<Word> answersPool = lesson.getAnswersPool();
        Collections.shuffle(answersPool, new SecureRandom());
        for (int i = 0; answers.size() < 5; i++) {
            if (!answersPool.get(i).equals(currentWord)) {
                answers.add(answersPool.get(i));
            }
        }
        Collections.shuffle(answers, new SecureRandom());
        return answers;
    }
}