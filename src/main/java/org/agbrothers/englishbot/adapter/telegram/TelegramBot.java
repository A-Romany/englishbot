package org.agbrothers.englishbot.adapter.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is a telegram bot that sends response messages to the user.
 */
@Component
public class TelegramBot extends TelegramWebhookBot {

    /*
      Init the telegram context
     */
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
     * @throws TelegramApiRequestException
     */
    @PostConstruct
    public void instantiateBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(this);
    }

    /**
     * Send text message to telegram chat
     *
     * @param recipientId the id of the user
     * @param text        the text of the message
     */
    public void sendTextMessage(String recipientId, String text) {
        SendMessage sendMessage = new SendMessage().setChatId(recipientId).setText(text).setParseMode(ParseMode.HTML);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send quick reply message (with buttons) to telegram chat
     *
     * @param recipientId   the id of the user
     * @param question      the text of the question
     * @param answerOptions the options of the answer to the question
     */
    public void sendQuickReply(String recipientId, String question, Map<String, String> answerOptions) {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup replyKeyboard = new InlineKeyboardMarkup();
        // add answers to the keyboard
        answerOptions.entrySet().iterator().forEachRemaining(e -> {
            replyKeyboard.getKeyboard().add(createInlineKeyboardButtonsRow(e));
        });

        sendMessage.setReplyMarkup(replyKeyboard)
                .setChatId(recipientId)
                .setText(question)
                .setParseMode(ParseMode.HTML);

        try {
            // send the reply
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private List<InlineKeyboardButton> createInlineKeyboardButtonsRow(Map.Entry<String, String> answerOption) {
        String key = answerOption.getKey();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        if (key.toLowerCase().contains("https://") || key.toLowerCase().contains("http://")) {
            keyboardButton.setText(answerOption.getValue()).setUrl(key);
        } else {
            keyboardButton.setText(answerOption.getValue()).setCallbackData(key);
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

