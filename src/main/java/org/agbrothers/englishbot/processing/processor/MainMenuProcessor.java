package org.agbrothers.englishbot.processing.processor;

import org.agbrothers.englishbot.buttonsbuilder.MainMenuButtonsBuilder;
import org.agbrothers.englishbot.messagebuilder.MainMenuMessageBuilder;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MainMenuProcessor implements Processor {

    private MainMenuMessageBuilder mainMenuMessageBuilder;
    private MainMenuButtonsBuilder mainMenuButtonsBuilder;

    @Override
    public void process(ProcessingExchange exchange) {
        String requestMessageText = exchange.getMessageText();
        String responseMessageText = mainMenuMessageBuilder.getResponseMessageText(requestMessageText);
        exchange.setResponseMessageText(responseMessageText);

        Map<String, String> keyboardButtons = mainMenuButtonsBuilder.getKeyboardButtons(requestMessageText);
        exchange.setResponseButtons(keyboardButtons);
    }

    @Autowired
    public void setMainMenuMessageBuilder(MainMenuMessageBuilder mainMenuMessageBuilder) {
        this.mainMenuMessageBuilder = mainMenuMessageBuilder;
    }

    @Autowired
    public void setMainMenuButtonsBuilder(MainMenuButtonsBuilder mainMenuButtonsBuilder) {
        this.mainMenuButtonsBuilder = mainMenuButtonsBuilder;
    }
}
