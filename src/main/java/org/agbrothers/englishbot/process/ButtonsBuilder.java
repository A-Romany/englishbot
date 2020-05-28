package org.agbrothers.englishbot.process;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static constant.ButtonLabel.*;

@Component
public class ButtonsBuilder {

    public Map<String, String> getKeyboardButtons(String label){
        Map<String, String> keyboardMap;
        switch ( label) {
            case LESSONS:
                keyboardMap = new HashMap<>();
                keyboardMap.put(ENGLISH, ENGLISH);
                keyboardMap.put(UKRAINIAN, UKRAINIAN);
                return keyboardMap;
            case DICTIONARY:
                keyboardMap = new HashMap<>();
                keyboardMap.put(PRINT_ALL_WORD, PRINT_ALL_WORD);
                keyboardMap.put(ADD_WORD, ADD_WORD);
                keyboardMap.put(EDIT_DICTIONARY, EDIT_DICTIONARY);
                keyboardMap.put(REMOVE_WORD, REMOVE_WORD);
                return  keyboardMap;
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
