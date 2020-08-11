package org.agbrothers.englishbot.menu;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.process.ButtonsBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;
import static org.agbrothers.englishbot.constant.LinkLabel.*;

@Component
public class MainMenuButtonsBuilder extends ButtonsBuilder {

    @Override
    public Map<String, String> getKeyboardButtons(String messageText) {

        Map<String, String> keyboardMap;
        switch (messageText) {
            case LESSONS:
                keyboardMap = new HashMap<>();
                keyboardMap.put(ButtonLabel.ENGLISH, "From English to Ukrainian");
                keyboardMap.put(ButtonLabel.UKRAINIAN, "From Ukrainian to English");
                return keyboardMap;
            case DICTIONARY:
                keyboardMap = new HashMap<>();
                keyboardMap.put(PRINT_ALL_WORD, PRINT_ALL_WORD);
                keyboardMap.put(ADD_WORD, ADD_WORD);
                keyboardMap.put(EDIT_DICTIONARY, EDIT_DICTIONARY);
                keyboardMap.put(REMOVE_WORD, REMOVE_WORD);
                return keyboardMap;
            case MAIN_MENU:
                keyboardMap = new HashMap<>();
                keyboardMap.put(DICTIONARY, DICTIONARY);
                keyboardMap.put(LESSONS, LESSONS);
                return keyboardMap;
            default:
                return null;
        }
    }
}
