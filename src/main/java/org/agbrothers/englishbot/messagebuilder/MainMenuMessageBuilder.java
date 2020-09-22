package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;
import static org.agbrothers.englishbot.constant.LinkLabel.*;

@Component
public class MainMenuMessageBuilder extends MessageBuilder {

    private static final String MAKE_CHOICE = "Будь-ласка зробіть свій вибір";

    @Override
    public String getResponseMessageText(String label) {

        switch (label) {
            case LESSONS:
                return getChoiceMadeText(LESSONS) + MAKE_CHOICE +
                        RETURN_MAIN_MENU;
            case DICTIONARY:
                return getChoiceMadeText(DICTIONARY) + MAKE_CHOICE +
                        RETURN_MAIN_MENU;
            case MAIN_MENU:
                return MAKE_CHOICE;
            case REMOVE_WORD:
                return getChoiceMadeText(REMOVE_WORD) + " Триває розробка видалення слів зі словника" +
                        RETURN_MAIN_MENU;
            case EDIT_DICTIONARY:
                return getChoiceMadeText(EDIT_DICTIONARY) + " Триває розробка редагування слів зі словника" +
                        RETURN_MAIN_MENU;
            case PRINT_ALL_WORD:
                return getChoiceMadeText(PRINT_ALL_WORD) + " Триває розробка друку слів зі словника" +
                        RETURN_MAIN_MENU;
            case ADD_WORD:
                return getChoiceMadeText(ADD_WORD) + " Триває розробка додавання слів до словника" +
                        RETURN_MAIN_MENU;
            case ButtonLabel.UKRAINIAN:
                return getChoiceMadeText(ButtonLabel.UKRAINIAN) + " Триває розробка уроку" +
                        RETURN_MAIN_MENU;
            default:
                return "Не розумію, що ти маєш на увазі?" + RETURN_MAIN_MENU;
        }
    }
}