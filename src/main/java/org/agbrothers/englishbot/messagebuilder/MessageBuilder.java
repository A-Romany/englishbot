package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.StringPart;
import org.springframework.stereotype.Component;

@Component
public abstract class MessageBuilder {

    public static String getHelloMessage() {
        return CommonPhrase.HELLO_MESSAGE;
    }

    public abstract String getResponseMessageText(String messageText);

    protected String getChoiceMadeText(String chosenLabel) {
        return CommonPhrase.YOU_CHOOSE + chosenLabel + StringPart.POINT;
    }
}
