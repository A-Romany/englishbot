package org.agbrothers.englishbot.process;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.lesson.EnglishLessonButtonBuilder;
import org.agbrothers.englishbot.lesson.EnglishLessonMessageBuilder;
import org.agbrothers.englishbot.lesson.Lesson;
import org.agbrothers.englishbot.menu.MainMenuButtonsBuilder;
import org.agbrothers.englishbot.menu.MainMenuMessageBuilder;
import org.agbrothers.englishbot.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.agbrothers.englishbot.constant.State.MAIN_MENU;



@Component
public class ProcessingCore {
    private TelegramBot telegramBot;
    private LessonService lessonService;
    private MessageBuilder messageBuilder;
    private ButtonsBuilder buttonsBuilder;
    private Map<String, String> stepRegistry = new HashMap<>();

    public void processMessage(String chatId, String messageText) {
        String stateId = getStateId(chatId);
        if(stateId == null){
            sendHelloMessage(chatId);
            stepRegistry.put(chatId, State.MAIN_MENU);
        }

        switch (messageText) {
            case LinkLabel.MAIN_MENU:
                stepRegistry.put(chatId, State.MAIN_MENU);
                break;
            case ButtonLabel.ENGLISH:
                stepRegistry.put(chatId, State.ENGLISH_LESSON);
                break;
            case ButtonLabel.UKRAINIAN:
                stepRegistry.put(chatId, State.UKRAINIAN_LESSON);
                break;
        }

        stateId = getStateId(chatId);
        messageBuilder = getMessageBuilder(stateId, chatId);
        String responseMessageText = messageBuilder.getResponseMessageText(messageText);
        buttonsBuilder = getButtonsBuilder(stateId, chatId);
        Map<String, String> keyboardButtons = buttonsBuilder.getKeyboardButtons(messageText);

        if(keyboardButtons == null){
            telegramBot.sendTextMessage(chatId, responseMessageText);
        } else {
            telegramBot.sendMessageWithKeyboard(chatId, responseMessageText, keyboardButtons);
        }
    }

    private MessageBuilder getMessageBuilder(String stateId, String chatId) {
        switch (stateId){
            case MAIN_MENU:
                return new MainMenuMessageBuilder();
            case State.ENGLISH_LESSON:
                Lesson lesson = lessonService.getLesson(chatId);
                return new EnglishLessonMessageBuilder(lesson);
            default:
                return null;
        }
    }

    private ButtonsBuilder getButtonsBuilder(String stateId, String chatId){
        switch (stateId) {
            case MAIN_MENU:
                return new MainMenuButtonsBuilder();
            case State.ENGLISH_LESSON:
                Lesson lesson = lessonService.getLesson(chatId);
                return new EnglishLessonButtonBuilder(lesson);
            default:
                return null;
        }
    }

    private String getStateId(String chatId) {
        return stepRegistry.get(chatId);
    }

    public void sendHelloMessage(String chatId){
        telegramBot.sendTextMessage(chatId, MessageBuilder.getHelloMessage());
    }

    @Autowired
    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }
}
