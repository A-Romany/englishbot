package org.agbrothers.englishbot.processing;

import org.agbrothers.englishbot.entity.ResponseMessage;
import org.agbrothers.englishbot.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ProcessingExchange {
    private User user;
    private String messageText;
    private List<ResponseMessage> responseMessageList = new ArrayList<>();
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

    public void appendResponseMessageText(String textToAppend) {

        if (responseMessageList.size() < 1) {
            responseMessageList.add(new ResponseMessage());
        }

        ResponseMessage responseMessage = getResponseMessageList().get(0);
        String responseMessageText = responseMessage.getResponseMessageText();

        if (responseMessageText.equals("")) {
            responseMessage.setResponseMessageText(textToAppend);
        } else {
            responseMessage.setResponseMessageText(responseMessageText + "\n" + textToAppend);
        }
    }

    public String getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(String exchangeState) {
        this.exchangeState = exchangeState;
    }

    public List<ResponseMessage> getResponseMessageList() {
        return responseMessageList;
    }

}
