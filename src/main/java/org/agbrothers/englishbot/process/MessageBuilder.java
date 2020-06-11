package org.agbrothers.englishbot.process;

import org.springframework.stereotype.Component;

import static constant.ButtonLabel.*;
import static constant.ButtonLabel.ENGLISH;

@Component
public class MessageBuilder {

    private static final String HELLO_MESSAGE = "Привіт, я дуже радий, що ти хочеш покращити свої знання англійської мови!!!" +
            " Маю надію, що я тобі в цьому допоможу!))"+
            "Для початку навчання натисни "+MAIN_MENU;
    private static final String MAKE_CHOICE = "Будь-ласка зробіть свій вибір";
    private static final String RETURN_MAIN_MENU = "  Для повернення в головне меню натисніть "+ MAIN_MENU;
    private static final String YOU_CHOOSE = "Ви вибрали ";

    public String getResponseMessageText(String label) {
        switch (label) {
            case LESSONS:
                return  getChoiceMadeText(LESSONS) + MAKE_CHOICE +
                        RETURN_MAIN_MENU;
            case DICTIONARY:
                return  getChoiceMadeText(DICTIONARY) + MAKE_CHOICE +
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
            case UKRAINIAN:
                return getChoiceMadeText(UKRAINIAN) + " Триває розробка уроку" +
                        RETURN_MAIN_MENU;
            case ENGLISH:
                return getChoiceMadeText(ENGLISH) + " Триває розробка уроку" +
                        RETURN_MAIN_MENU;
            default:
                return "НЕ ПИШИ ВСЯКУ ЄРЕСЬ"+ RETURN_MAIN_MENU;
        }
    }

    public String getHelloMessage(){
        return HELLO_MESSAGE;
    }

    private String getChoiceMadeText(String chosenLabel){
        return YOU_CHOOSE + chosenLabel + ".";
    }

}
