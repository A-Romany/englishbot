package org.agbrothers.englishbot.process;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.agbrothers.englishbot.buttonsbuilder.*;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.messagebuilder.*;
import org.agbrothers.englishbot.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ProcessingCore {
    private TelegramBot telegramBot;
    private LessonService lessonService;
    private Map<String, String> stepRegistry = new HashMap<>();
    private ApplicationContext springApplicationContext;

    public void processMessage(String chatId, String messageText) {
        String stateId = getStateId(chatId);
        if(stateId == null){
            sendHelloMessage(chatId);
            stepRegistry.put(chatId, State.MAIN_MENU);
        }

        switch (messageText) {
            case LinkLabel.MAIN_MENU:
                stepRegistry.put(chatId, State.MAIN_MENU);
                lessonService.removeLesson(chatId);
                break;
            case ButtonLabel.ENGLISH:
                stepRegistry.put(chatId, State.ENGLISH_LESSON);
                break;
            case ButtonLabel.UKRAINIAN:
                stepRegistry.put(chatId, State.UKRAINIAN_LESSON);
                break;
            case ButtonLabel.ADD_WORD:
                stepRegistry.put(chatId, State.ADD_WORD_TO_DICTIONARY);
                break;
            case ButtonLabel.REMOVE_WORD:
                stepRegistry.put(chatId,State.DELETING_WORD);
                break;
            case ButtonLabel.PRINT_ALL_WORD:
                stepRegistry.put(chatId, State.PRINTING_WORDS);
                break;
            case ButtonLabel.DICTIONARY:
                stepRegistry.put(chatId, State.DICTIONARY);
                break;
        }

        stateId = getStateId(chatId);
        MessageBuilder messageBuilder = getMessageBuilder(stateId, chatId);
        String responseMessageText = messageBuilder.getResponseMessageText(messageText);
        ButtonsBuilder buttonsBuilder = getButtonsBuilder(stateId, chatId);
        Map<String, String> keyboardButtons = buttonsBuilder.getKeyboardButtons(messageText);

        if(keyboardButtons == null){
            telegramBot.sendTextMessage(chatId, responseMessageText);
        } else {
            telegramBot.sendMessageWithKeyboard(chatId, responseMessageText, keyboardButtons);
        }
    }

    private MessageBuilder getMessageBuilder(String stateId, String chatId) {
        switch (stateId){
            case State.ENGLISH_LESSON:
                return new EnglishLessonMessageBuilder(lessonService.getLesson(chatId));
            case State.UKRAINIAN_LESSON:
                return new UkrainianLessonMessageBuilder(lessonService.getLesson(chatId));
            case State.ADD_WORD_TO_DICTIONARY:
                return springApplicationContext.getBean(AddWordMessageBuilder.class);
            case State.DELETING_WORD:
                return springApplicationContext.getBean(DeletingWordMessageBuilder.class);
            case State.PRINTING_WORDS:
                return springApplicationContext.getBean(PrintingWordsMessageBuilder.class);
            case State.DICTIONARY:
                return new DictionaryMessageBuilder();
            default:
            case State.MAIN_MENU:
                return new MainMenuMessageBuilder();
        }
    }

    private ButtonsBuilder getButtonsBuilder(String stateId, String chatId){
        switch (stateId) {
            case State.ENGLISH_LESSON:
                return new EnglishLessonButtonBuilder(lessonService.getLesson(chatId));
            case State.UKRAINIAN_LESSON:
                return new UkrainianLessonButtonBuilder(lessonService.getLesson(chatId));
            case State.PRINTING_WORDS:
                return springApplicationContext.getBean(PrintingWordsButtonBuilder.class);
            default:
            case State.MAIN_MENU:
                return new MainMenuButtonsBuilder();
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

    @Autowired
    public void setSpringApplicationContext(ApplicationContext springApplicationContext) {
        this.springApplicationContext = springApplicationContext;
    }
}
