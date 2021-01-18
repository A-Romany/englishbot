package org.agbrothers.englishbot.messagebuilder;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;

public class DictionaryMessageBuilder extends MessageBuilder{

    @Override
    public String getResponseMessageText(String label) {
        switch (label) {
            case EDIT_DICTIONARY:
                return getChoiceMadeText(EDIT_DICTIONARY) + " Триває розробка редагування слів зі словника" +
                        RETURN_MAIN_MENU;
            case PRINT_ALL_WORD:
                return getChoiceMadeText(PRINT_ALL_WORD) + " Триває розробка друку слів зі словника" +
                        RETURN_MAIN_MENU;
            default:
                return "Я вас не розумію. Повернутися в головне меню? " + RETURN_MAIN_MENU;
        }
    }
}
