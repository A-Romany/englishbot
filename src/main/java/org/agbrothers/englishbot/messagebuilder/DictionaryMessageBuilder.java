package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;

@Component
public class DictionaryMessageBuilder extends MessageBuilder {

    @Override
    public String getResponseMessageText(String label) {
        if (EDIT_DICTIONARY.equals(label)) {
            return getChoiceMadeText(EDIT_DICTIONARY) + CommonPhrase.DEVELOPMENT_OF_DIRECTION +
                    CommonPhrase.RETURN_MAIN_MENU;
        }
        return CommonPhrase.INCORRECT_MESSAGE + CommonPhrase.RETURN_MAIN_MENU;
    }
}
