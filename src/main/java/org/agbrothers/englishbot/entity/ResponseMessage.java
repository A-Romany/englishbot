package org.agbrothers.englishbot.entity;

import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.List;
import java.util.Map;

public class ResponseMessage {
    private String responseMessageText = "";
    private List<Map<String, String>> responseButtons;
    private InputFile audio;

    public ResponseMessage() {
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

    public void setResponseButtons(List<Map<String, String>> responseButtons) {
        this.responseButtons = responseButtons;
    }

    public InputFile getAudio() {
        return audio;
    }

    public void setAudio(InputFile audio) {
        this.audio = audio;
    }
}
