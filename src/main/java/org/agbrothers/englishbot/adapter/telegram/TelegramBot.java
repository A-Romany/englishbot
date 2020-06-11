package org.agbrothers.englishbot.adapter.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * This is a telegram bot that sends response messages to the user.
 */
@Component
public class TelegramBot extends TelegramWebhookBot {

    static {
        ApiContextInitializer.init();
    }

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.name}")
    private String botName;

    /**
     * Register this component to be used for processing Telegram updates.
     *
     * @throws TelegramApiRequestException when something goes wrong
     */
    @PostConstruct
    public void instantiateBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(this);
    }

    /**
     * Send text message to telegram chat
     *
     * @param chatId the id of the user
     * @param text        the text of the message
     */
    public void sendTextMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(text).setParseMode(ParseMode.HTML);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send quick reply message (with buttons) to telegram chat
     *
     * @param chatId   the id of the user
     * @param messageText   the text of the message
     * @param buttonLabels  the labels for buttons
     */
    public void sendMessageWithKeyboard(String chatId, String messageText, Map<String, String> buttonLabels) {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup replyKeyboard = new InlineKeyboardMarkup();
        // add answers to the keyboard
        buttonLabels.entrySet()
                .iterator()
                .forEachRemaining(e -> replyKeyboard.getKeyboard().add(createInlineKeyboardButtonsRow(e)));

        sendMessage.setReplyMarkup(replyKeyboard)
                .setChatId(chatId)
                .setText(messageText)
                .setParseMode(ParseMode.HTML);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private List<InlineKeyboardButton> createInlineKeyboardButtonsRow(Map.Entry<String, String> button) {
        String key = button.getKey();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        if (key.toLowerCase().contains("https://") || key.toLowerCase().contains("http://")) {
            keyboardButton.setText(button.getValue()).setUrl(key);
        } else {
            keyboardButton.setText(button.getValue()).setCallbackData(key);
        }
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(keyboardButton);
        return row;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}

