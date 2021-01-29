package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;

@Component
public class DictionaryMessageBuilder extends MessageBuilder{

    @Override
    public String getResponseMessageText(String label) {
        switch (label) {
            case EDIT_DICTIONARY:
                return getChoiceMadeText(EDIT_DICTIONARY) + " Триває розробка редагування слів зі словника" +
                        CommonPhrase.RETURN_MAIN_MENU;
            default:
                return "Я вас не розумію. Повернутися в головне меню? " + CommonPhrase.RETURN_MAIN_MENU;
        }
    }
}
