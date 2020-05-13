package org.agbrothers.englishbot.process;

import org.agbrothers.englishbot.adapter.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessingCore {

    private TelegramBot telegramBot;

    public void processMessage(String chatId, String messageText) {
        telegramBot.sendTextMessage(chatId, messageText);
    }

    @Autowired
    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

}
