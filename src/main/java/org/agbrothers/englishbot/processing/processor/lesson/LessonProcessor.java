package org.agbrothers.englishbot.processing.processor.lesson;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.constant.StringPart;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.ResponseMessage;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class LessonProcessor implements Processor {

    private LessonService lessonService;

    protected abstract String getCorrectAnswer(Word word);

    protected abstract String getValueToTranslate(Word word);

    protected abstract List<Map<String, String>> formAnswersMap(List<Word> answers);

    @Override
    public void process(ProcessingExchange exchange) {
        Lesson lesson = lessonService.getLesson(exchange.getUser());
        String messageText = exchange.getMessageText();

        ResponseMessage responseMessage = new ResponseMessage();
        exchange.getResponseMessages().add(responseMessage);

        String responseMessageText = getResponseMessageText(messageText, lesson);
        exchange.appendResponseMessageText(responseMessageText);

        responseMessage.setResponseButtons(getKeyboardButtons(lesson));

        exchange.setExchangeState(State.READY_TO_SEND);
    }

    protected String getResponseMessageText(String messageText, Lesson lesson) {
        String result = "";
        if (lesson.getCurrentWord() != null) {
            String correctAnswer = getCorrectAnswer(lesson.getCurrentWord());
            if (correctAnswer.equals(messageText)) {
                lesson.setCountCorrectAnswers(lesson.getCountCorrectAnswers() + 1);
                result = CommonPhrase.CORRECT_ANSWER;
            } else {
                lesson.setCountIncorrectAnswer(lesson.getCountIncorrectAnswer() + 1);
                result = CommonPhrase.INCORRECT_ANSWER + correctAnswer + StringPart.NEWLINE;
            }
        }

        Word wordQuestion = lessonService.getNextWord(lesson);
        if (wordQuestion == null) {
            return result + String.format(CommonPhrase.LESSON_ENDING,
                    lesson.getCountCorrectAnswers(),
                    (lesson.getCountIncorrectAnswer() + lesson.getCountCorrectAnswers()));
        } else {
            return result + getValueToTranslate(wordQuestion);
        }
    }

    protected List <Map<String, String>> getKeyboardButtons(Lesson lesson) {
        List<Word> answers = lessonService.getAnswers(lesson);
         if (!answers.isEmpty()) {
            return formAnswersMap(answers);
        }
        return getMenuButtons();
    }

    protected List<Map<String, String>> getMenuButtons() {
        List<Map<String, String>> keyboardMaps = new ArrayList<>();
        Map<String, String> keyboardMap = new LinkedHashMap<>();
        keyboardMap.put(ButtonLabel.ENGLISH, ButtonLabel.ENGLISH);
        keyboardMap.put(ButtonLabel.ENGLISH_AUDIO, ButtonLabel.ENGLISH_AUDIO);
        keyboardMap.put(ButtonLabel.UKRAINIAN, ButtonLabel.UKRAINIAN);
        keyboardMaps.add(keyboardMap);

        return keyboardMaps;
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    public LessonService getLessonService() {
        return lessonService;
    }
}