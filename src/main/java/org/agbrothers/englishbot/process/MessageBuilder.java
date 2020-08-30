package org.agbrothers.englishbot.process;

import org.agbrothers.englishbot.constant.MessageLabel;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.LinkLabel.MAIN_MENU;

@Component
public abstract class MessageBuilder {

    private static final String HELLO_MESSAGE = "Привіт, я дуже радий, що ти хочеш покращити свої знання англійської мови!!!" +
            " Маю надію, що я тобі в цьому допоможу!))"+
            "Для початку навчання натисни "+MAIN_MENU;

    protected static final String YOU_CHOOSE = "Ви вибрали ";
    protected static final String RETURN_MAIN_MENU = "  Для повернення в головне меню натисніть "+ MAIN_MENU;

    public static String getHelloMessage(){
        return HELLO_MESSAGE;
    }

    public abstract String getResponseMessageText(String messageText);

    protected String getChoiceMadeText(String chosenLabel){
        return YOU_CHOOSE + chosenLabel + MessageLabel.POINT;
    }

}
