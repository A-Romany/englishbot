package org.agbrothers.englishbot.processing;

import org.agbrothers.englishbot.constant.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.agbrothers.englishbot.constant.LinkLabel.MAIN_MENU;

public class ProcessingExchange {
    private String state;
    private String chatId;
    private String messageText;
    private String responseMessageText;
    private List <Map<String, String>> responseButtons;

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

    public List<Map<String, String>> getResponseButtons() {
        return responseButtons;
    }

    public void setResponseButtons(List <Map<String, String>> responseButtons) {
        this.responseButtons = responseButtons;
        addMainMenuButton(responseButtons);
    }

    public List<Map<String,String>> addMainMenuButton(List<Map<String, String>> result) {
        if (!state.equals(State.MAIN_MENU)) {
            Map<String, String> mainMenuButton = new HashMap<>();
            mainMenuButton.put(MAIN_MENU, "Повернутись в головне меню");
            result.add(mainMenuButton);
        }
        return result;
    }
}
