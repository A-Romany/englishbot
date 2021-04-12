package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.DICTIONARY;
import static org.agbrothers.englishbot.constant.ButtonLabel.LESSONS;
import static org.agbrothers.englishbot.constant.CommonPhrase.*;

@Component
public class MainMenuMessageBuilder extends MessageBuilder {

    @Override
    public String getResponseMessageText(String label) {
        switch (label) {
            case START:
            case MAIN_MENU:
                return MAKE_CHOICE;
            case DICTIONARY:
                return getChoiceMadeText(DICTIONARY) + MAKE_CHOICE;
            case LESSONS:
                return getChoiceMadeText(LESSONS) + MAKE_CHOICE;
            default:
                return CommonPhrase.INCORRECT_MESSAGE + RETURN_TO_MAIN_MENU;
        }
    }
}
