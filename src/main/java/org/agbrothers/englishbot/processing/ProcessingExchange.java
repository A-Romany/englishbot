package org.agbrothers.englishbot.processing;

import java.util.Map;

public class ProcessingExchange {
    private String state;
    private String chatId;
    private String messageText;
    private String responseMessageText;
    private Map<String, String> responseButtons;

    public ProcessingExchange(String state, String chatId, String messageText) {
        this.state = state;
        this.chatId = chatId;
        this.messageText = messageText;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getResponseMessageText() {
        return responseMessageText;
    }

    public void setResponseMessageText(String responseMessageText) {
        this.responseMessageText = responseMessageText;
    }

    public Map<String, String> getResponseButtons() {
        return responseButtons;
    }

    public void setResponseButtons(Map<String, String> responseButtons) {
        this.responseButtons = responseButtons;
    }
}
