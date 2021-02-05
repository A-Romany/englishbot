package org.agbrothers.englishbot.buttonsbuilder;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;
import static org.agbrothers.englishbot.constant.LinkLabel.*;

@Component
public class MainMenuButtonsBuilder extends ButtonsBuilder {

    @Override
    public List <Map<String, String>> getKeyboardButtons(String messageText) {

        List <Map<String, String>> keyboardMaps= new ArrayList<>();
        Map<String, String> keyboardMap = new HashMap<>();
        switch (messageText) {
            case LESSONS:
                keyboardMap.put(ButtonLabel.ENGLISH, "From English to Ukrainian");
                keyboardMap.put(ButtonLabel.UKRAINIAN, "From Ukrainian to English");
                keyboardMaps.add(keyboardMap);
                return keyboardMaps;

            case DICTIONARY:
                keyboardMap.put(PRINT_ALL_WORD, PRINT_ALL_WORD);
                keyboardMap.put(ADD_WORD, ADD_WORD);
                keyboardMap.put(EDIT_DICTIONARY, EDIT_DICTIONARY);
                keyboardMap.put(REMOVE_WORD, REMOVE_WORD);
                keyboardMaps.add(keyboardMap);
                return keyboardMaps;

            case MAIN_MENU:
                keyboardMap.put(DICTIONARY, DICTIONARY);
                keyboardMap.put(LESSONS, LESSONS);
                keyboardMaps.add(keyboardMap);
                return keyboardMaps;

            default:
                return new ArrayList<>();
        }
    }
}
