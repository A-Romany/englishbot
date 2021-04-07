package org.agbrothers.englishbot.processing;

import org.agbrothers.englishbot.entity.User;

import java.util.List;
import java.util.Map;

public class ProcessingExchange {
    private User user;
    private String messageText;
    private String responseMessageText = "";
    private List <Map<String, String>> responseButtons;
    private String exchangeState;

    public ProcessingExchange(User user, String messageText) {
        this.user = user;
        this.messageText = messageText;
    }

    public User getUser() {
        return user;
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

    public void appendResponseMessageText(String responseMessageText) {
        this.responseMessageText = this.responseMessageText + "\n" + responseMessageText;
    }

    public List<Map<String, String>> getResponseButtons() {
        return responseButtons;
    }

    public void setResponseButtons(List <Map<String, String>> responseButtons) {
        this.responseButtons = responseButtons;
    }

    public String getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(String exchangeState) {
        this.exchangeState = exchangeState;
    }
}
