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
import java.util.Map;

@Component
public class ProcessingCore {
    private TelegramBot telegramBot;
    private LessonService lessonService;
    private final Map<String, String> stepRegistry = new HashMap<>();
    private ProcessorHolder processorHolder;

    public void processMessage(String chatId, String messageText) {
        String stateId = getStateId(chatId);
        if (stateId == null) {
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
                stepRegistry.put(chatId, State.DELETING_WORD);
                break;
            case ButtonLabel.PRINT_ALL_WORD:
                stepRegistry.put(chatId, State.PRINTING_WORDS);
                break;
            case ButtonLabel.EDIT_DICTIONARY:
                stepRegistry.put(chatId, State.DICTIONARY);
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
            telegramBot.sendMessageWithKeyboard(chatId, exchange.getResponseMessageText(), exchange.getResponseButtons());
        }
    }

    private String getStateId(String chatId) {
        return stepRegistry.get(chatId);
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
