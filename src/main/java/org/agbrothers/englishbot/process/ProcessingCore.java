package org.agbrothers.englishbot.process;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ProcessingCore {

    private TelegramBot telegramBot;
    private MessageBuilder messageBuilder;
    private ButtonsBuilder buttonsBuilder;

    public void processMessage(String chatId, String messageText) {
        String responseMessageText = messageBuilder.getResponseMessageText(messageText);
        Map<String, String> keyboardButtons = buttonsBuilder.getKeyboardButtons(messageText);

        if(keyboardButtons == null){
            telegramBot.sendTextMessage(chatId, responseMessageText);
        } else {
            telegramBot.sendMessageWithKeyboard(chatId, responseMessageText, keyboardButtons);
        }
    }

    public void sendHelloMessage(String chatId){
        telegramBot.sendTextMessage(chatId, messageBuilder.getHelloMessage());
    }

    @Autowired
    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Autowired
    public void setMessageBuilder(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Autowired
    public void setButtonsBuilder(ButtonsBuilder buttonsBuilder) {
        this.buttonsBuilder = buttonsBuilder;
    }
}
