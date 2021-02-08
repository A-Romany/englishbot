package org.agbrothers.englishbot.processing;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.messagebuilder.MessageBuilder;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.processing.processor.ProcessorHolder;
import org.agbrothers.englishbot.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.agbrothers.englishbot.constant.LinkLabel.MAIN_MENU;

@Component
public class ProcessingCore {
    private TelegramBot telegramBot;
    private LessonService lessonService;
    private final Map<String, String> stateByChatId = new HashMap<>();
    private ProcessorHolder processorHolder;

    public void processUserRequest(String chatId, String messageText) {
        String stateId = getStateId(chatId);
        if (stateId == null) {
            sendHelloMessage(chatId);
            stateByChatId.put(chatId, State.MAIN_MENU);
        }

        switch (messageText) {
            case LinkLabel.MAIN_MENU:
                stateByChatId.put(chatId, State.MAIN_MENU);
                lessonService.removeLesson(chatId);
                break;
            case ButtonLabel.ENGLISH:
                stateByChatId.put(chatId, State.ENGLISH_LESSON);
                break;
            case ButtonLabel.UKRAINIAN:
                stateByChatId.put(chatId, State.UKRAINIAN_LESSON);
                break;
            case ButtonLabel.ADD_WORD:
                stateByChatId.put(chatId, State.ADD_WORD_TO_DICTIONARY);
                break;
            case ButtonLabel.REMOVE_WORD:
                stateByChatId.put(chatId, State.DELETING_WORD);
                break;
            case ButtonLabel.PRINT_ALL_WORD:
                stateByChatId.put(chatId, State.PRINTING_WORDS);
                break;
            case ButtonLabel.EDIT_DICTIONARY:
                stateByChatId.put(chatId, State.DICTIONARY);
                break;
        }

        stateId = getStateId(chatId);

        ProcessingExchange exchange = new ProcessingExchange(stateId, chatId, messageText);

        Processor processor = processorHolder.getProcessorByState(stateId);
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
            if (!State.MAIN_MENU.equals(stateByChatId.get(chatId))) {
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

    private String getStateId(String chatId) {
        return stateByChatId.get(chatId);
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
    public void setProcessingChainConfig(ProcessorHolder processorHolder) {
        this.processorHolder = processorHolder;
    }
}
