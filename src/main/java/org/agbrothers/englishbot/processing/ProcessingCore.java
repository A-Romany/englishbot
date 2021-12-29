package org.agbrothers.englishbot.processing;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.entity.ResponseMessage;
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

import static org.agbrothers.englishbot.constant.CommonPhrase.MAIN_MENU;

@Component
public class ProcessingCore {
    private TelegramBot telegramBot;
    private LessonService lessonService;
    private ProcessorFactory processorFactory;
    private UserService userService;

    public void processUserRequest(String chatId, String messageText) {
        User user = userService.getUserByChatId(chatId);
        if (null == user) {
            user = userService.saveAndFlushUser(new User(chatId));
        }

        if (user.getStateId() == null) {
            sendHelloMessage(user.getChatId());
            user.setStateId(State.MAIN_MENU);
        }

        applyUserSate(messageText, user);

        ProcessingExchange exchange = new ProcessingExchange(user, messageText);

        processExchange(exchange);

        for (ResponseMessage responseMessage : exchange.getResponseMessages()) {
            if (responseMessage.getAudio() != null) {
                telegramBot.sendAudioMessage(user.getChatId(), responseMessage.getAudio());
            } else if (responseMessage.getResponseButtons() == null) {
                telegramBot.sendTextMessage(user.getChatId(), responseMessage.getResponseMessageText());
            } else {
                List<Map<String, String>> responseButtons = responseMessage.getResponseButtons();
                if (!State.MAIN_MENU.equals(user.getChatId())) {
                    responseButtons.add(getMainMenuButton());
                }
                telegramBot.sendMessageWithKeyboard(user.getChatId(), responseMessage.getResponseMessageText(), responseButtons);
            }
        }
        userService.saveAndFlushUser(user);
    }

    private void processExchange(ProcessingExchange exchange) {
        Processor processor = processorFactory.getProcessorByState(exchange.getUser().getStateId());

        try {
            do {
                processor.process(exchange);
            }
            while (!State.READY_TO_SEND.equals(exchange.getExchangeState()));
        } catch (ProcessingException e) {
            //log and send error message
            telegramBot.sendTextMessage(exchange.getUser().getChatId(), CommonPhrase.ERROR_MESSAGE);
        }
    }

    private void applyUserSate(String messageText, User user) {
        switch (messageText) {
            case CommonPhrase.MAIN_MENU:
                user.setStateId(State.MAIN_MENU);
                lessonService.removeLesson(user.getLesson(), user);
                break;
            case ButtonLabel.ENGLISH:
                user.setStateId(State.ENGLISH_LESSON);
                break;
            case ButtonLabel.ENGLISH_AUDIO:
                user.setStateId(State.ENGLISH_AUDIO_LESSON);
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
    }

    private Map<String, String> getMainMenuButton() {
        Map<String, String> mainMenuButton = new LinkedHashMap<>();
        mainMenuButton.put(MAIN_MENU, ButtonLabel.RETURN_MAIN_MENU);
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
    public void setProcessorFactory(ProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
