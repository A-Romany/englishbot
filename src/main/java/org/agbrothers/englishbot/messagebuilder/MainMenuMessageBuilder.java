package org.agbrothers.englishbot.messagebuilder;

import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.DICTIONARY;
import static org.agbrothers.englishbot.constant.ButtonLabel.LESSONS;
import static org.agbrothers.englishbot.constant.LinkLabel.MAIN_MENU;
import static org.agbrothers.englishbot.constant.MessageLabel.MAKE_CHOICE;

@Component
public class MainMenuMessageBuilder extends MessageBuilder {

    @Override
    public String getResponseMessageText(String label) {
        switch (label) {
            case MAIN_MENU:
                return MAKE_CHOICE;
            case DICTIONARY:
                return getChoiceMadeText(DICTIONARY) + MAKE_CHOICE +
                        RETURN_MAIN_MENU;
            case LESSONS:
                return getChoiceMadeText(LESSONS) + MAKE_CHOICE +
                        RETURN_MAIN_MENU;
            default:
                return "Не розумію, що ти маєш на увазі?" + RETURN_MAIN_MENU;
        }
    }
}
