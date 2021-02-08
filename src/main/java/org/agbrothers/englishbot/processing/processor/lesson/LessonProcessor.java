package org.agbrothers.englishbot.processing.processor.lesson;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.constant.MessageLabel;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class LessonProcessor implements Processor {

    private LessonService lessonService;

    protected abstract String getCorrectAnswer(Word word);

    protected abstract String getValueToTranslate(Word word);

    protected abstract Map<String, String> formAnswersMap(List<Word> answers);

    @Override
    public void process(ProcessingExchange exchange) {
        Lesson lesson = lessonService.getLesson(exchange.getChatId());
        String messageText = exchange.getMessageText();

        String responseMessageText = getResponseMessageText(messageText, lesson);
        exchange.setResponseMessageText(responseMessageText);

        exchange.setResponseButtons(getKeyboardButtons(lesson));
    }

    protected String getResponseMessageText(String messageText, Lesson lesson) {
        String result = "";
        if (lesson.getCurrentWord() != null) {
            String correctAnswer = getCorrectAnswer(lesson.getCurrentWord());
            if (correctAnswer.equals(messageText)) {
                lesson.setCountCorrectAnswers(lesson.getCountCorrectAnswers() + 1);
                result = MessageLabel.CORRECT_ANSWER;
            } else {
                lesson.setCountIncorrectAnswer(lesson.getCountIncorrectAnswer() + 1);
                result = MessageLabel.INCORRECT_ANSWER + correctAnswer + MessageLabel.NEWLINE;
            }
        }

        Word wordQuestion = lessonService.getNextWord(lesson);
        if (wordQuestion == null) {
            return result + String.format(MessageLabel.LESSON_ENDING,
                    lesson.getCountCorrectAnswers(),
                    (lesson.getCountIncorrectAnswer() + lesson.getCountCorrectAnswers()),
                    LinkLabel.ENGLISH,
                    CommonPhrase.RETURN_MAIN_MENU);
        } else {
            return result + getValueToTranslate(wordQuestion);
        }
    }

    protected Map<String, String> getKeyboardButtons(Lesson lesson) {
        List<Word> answers = lessonService.getAnswers(lesson.getCurrentWord(), lesson);
        if (answers == null) {
            return getMenuButtons();
        }
        return formAnswersMap(answers);
    }

    protected Map<String, String> getMenuButtons() {
        Map<String, String> keyboardMap = new LinkedHashMap<>();
        keyboardMap.put(ButtonLabel.ENGLISH, "From English to Ukrainian");
        keyboardMap.put(ButtonLabel.UKRAINIAN, "From Ukrainian to English");
        return keyboardMap;
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }
}