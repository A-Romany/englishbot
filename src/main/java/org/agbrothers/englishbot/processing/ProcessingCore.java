package org.agbrothers.englishbot.processing;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.messagebuilder.MessageBuilder;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.processing.processor.ProcessorFactory;
import org.agbrothers.englishbot.service.LessonService;
import org.agbrothers.englishbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.agbrothers.englishbot.constant.LinkLabel.MAIN_MENU;

@Component
public class ProcessingCore {
    private TelegramBot telegramBot;
    private LessonService lessonService;
    private ProcessorFactory processorFactory;
    private UserService userService;

    public void processUserRequest(String chatId, String messageText) {
        User user = userService.containsUser(chatId)
                ? userService.getUserByChatId(chatId)
                : new User(chatId);

        if (user.getStateId() == null) {
            sendHelloMessage(user.getChatId());
            user.setStateId(State.MAIN_MENU);
        }

        switch (messageText) {
            case LinkLabel.MAIN_MENU:
                user.setStateId(State.MAIN_MENU);
                lessonService.removeLesson(chatId);
                break;
            case ButtonLabel.ENGLISH:
                user.setStateId(State.ENGLISH_LESSON);
                break;
            case ButtonLabel.UKRAINIAN:
                user.setStateId(State.UKRAINIAN_LESSON);
                break;
            case ButtonLabel.ADD_WORD:
                user.setStateId(State.ADD_WORD_TO_DICTIONARY);
                break;
            case ButtonLabel.REMOVE_WORD:
                user.setStateId(State.DELETING_WORD);
                break;
            case ButtonLabel.PRINT_ALL_WORD:
                user.setStateId(State.PRINTING_WORDS);
                break;
            case ButtonLabel.EDIT_DICTIONARY:
                user.setStateId(State.DICTIONARY);
                break;
        }

        userService.saveAndFlushUser(user);
        String stateId = user.getStateId();

        ProcessingExchange exchange = new ProcessingExchange(stateId, chatId, messageText);

        Processor processor = processorFactory.getProcessorByState(stateId);
        try {
            processor.process(exchange);
        } catch (ProcessingException e) {
            //log and send error message
            telegramBot.sendTextMessage(chatId, CommonPhrase.ERROR_MESSAGE);
            return;
        }

        if (exchange.getResponseButtons() == null) {
            telegramBot.sendTextMessage(chatId, exchange.getResponseMessageText());
        } else {
            List<Map<String, String>> responseButtons = exchange.getResponseButtons();
            if (!State.MAIN_MENU.equals(user.getChatId())) {
                responseButtons.add(getMainMenuButton());
            }
            telegramBot.sendMessageWithKeyboard(chatId, exchange.getResponseMessageText(), responseButtons);
        }
    }

    private Map<String, String> getMainMenuButton() {
        Map<String, String> mainMenuButton = new LinkedHashMap<>();
        mainMenuButton.put(MAIN_MENU, "Повернутись в головне меню");
        return mainMenuButton;
    }

    public void sendHelloMessage(String chatId) {
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
    public void setProcessingChainConfig(ProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
