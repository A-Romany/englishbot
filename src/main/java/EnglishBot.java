//import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.objects.Update;
//import org.telegram.telegrambots.exceptions.TelegramApiException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

import static constant.ButtonLabel.*;

public class EnglishBot extends TelegramLongPollingBot {

    private boolean countHello=false;

    private static final String HELLO_MESSAGE = "Привіт, я дуже радий, що ти хочеш покращити свої знання англійської мови!!!" +
            " Маю надію, що я тобі в цьому допоможу!))"+
            "Для початку навчання натисни "+MAIN_MENU;
    private static final String MAKE_CHOICE = "Будь-ласка зробіть свій вибір";
    private static final String RETURN_MAIN_MENU = "  Для повернення в головне меню натисніть "+ MAIN_MENU;
    //private static  final String MENU_DICTIONARY = "Ви вибрали \"Dictionary\". " + MAKE_CHOICE + RETURN_MAIN_MENU;
    //private static final String MENU_LESSONS ="Ви вибрали \"Lessons\". " + MAKE_CHOICE + RETURN_MAIN_MENU;;
    private static final String BOT_TOKEN = "1153088687:AAGlMcZ6W2qvE_YaWlYkXTkg9W2Yg8XZL0U";
    //private static final String YOU_CHOSED = "Ви вибрали {buttonLabel}.";
    private static final String YOU_CHOSED = "Ви вибрали ";

    String responseForUser="";
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();

    @Override
    public void onUpdateReceived(Update update) { //FIXME RF-001 veeeeeery long method, split it into several smaller and avoid code duplications
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        keyboard = new ArrayList<>();
        row = new KeyboardRow();

        SendMessage message = new SendMessage()
                .setChatId(chatId);


        // Get First message //FIXME more informative comment please and doing method
        if (!countHello) {
            countHello = true;
            SendMessage FirstMessage = new SendMessage()
                    .setChatId(chatId)
                    .setText(HELLO_MESSAGE);
            try {
                execute(FirstMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        //FIXME RF-002 replace several ifs to switch statement


        getResponseForUser(messageText);
        message.setText(responseForUser);


        message.setReplyMarkup(keyboardMarkup.setKeyboard(getKeyboardMarkupForUser(messageText)));


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getResponseForUser(String label) {
        switch (label) {
            case LESSONS:
                responseForUser = getChoiceMadeText(LESSONS) + MAKE_CHOICE +
                                RETURN_MAIN_MENU;
                break;
            case DICTIONARY:
                responseForUser = getChoiceMadeText(DICTIONARY) + MAKE_CHOICE +
                                RETURN_MAIN_MENU;
                break;
            case MAIN_MENU:
                responseForUser = MAKE_CHOICE;
                break;
            case REMOVE_WORD:
                responseForUser = getChoiceMadeText(REMOVE_WORD) + " Триває розробка видалення слів зі словника" +
                                RETURN_MAIN_MENU;
                break;
            case EDIT_DICTIONARY:
                responseForUser = getChoiceMadeText(EDIT_DICTIONARY) + " Триває розробка редагування слів зі словника" +
                                RETURN_MAIN_MENU;
                break;
            case PRINT_ALL_WORD:
                responseForUser = getChoiceMadeText(PRINT_ALL_WORD) + " Триває розробка друку слів зі словника" +
                                RETURN_MAIN_MENU;
                break;
            case ADD_WORD:
                responseForUser = getChoiceMadeText(ADD_WORD) + " Триває розробка додавання слів до словника" +
                                RETURN_MAIN_MENU;
                break;
            case UKRAINIAN:
                responseForUser = getChoiceMadeText(UKRAINIAN) + " Триває розробка уроку" +
                        RETURN_MAIN_MENU;
                break;
            case ENGLISH:
                responseForUser = getChoiceMadeText(ENGLISH) + " Триває розробка уроку" +
                        RETURN_MAIN_MENU;
                break;
            default:
                responseForUser = "НЕ ПИШИ ВСЯКУ ЄРЕСЬ"+ RETURN_MAIN_MENU;
        }
        return responseForUser;
    }

        public List getKeyboardMarkupForUser(String label){
            switch ( label) {
                case LESSONS:
                    KeyboardRow row = new KeyboardRow();
                    row.add(ENGLISH);
                    row.add(UKRAINIAN);
                    keyboard.add(row);
                    break;
                case DICTIONARY:
                    row = new KeyboardRow();
                    row.add(PRINT_ALL_WORD);
                    row.add(ADD_WORD);
                    keyboard.add(row);
                    row = new KeyboardRow();
                    row.add(EDIT_DICTIONARY);
                    row.add(REMOVE_WORD);
                    keyboard.add(row);
                    break;
                case MAIN_MENU:
                    keyboard = new ArrayList<>();
                    row = new KeyboardRow();
                    row.add(DICTIONARY);
                    row.add(LESSONS);
                    keyboard.add(row);
                    break;
                default:
                    keyboard = new ArrayList<>();
            }
            return keyboard;
    }


    private String getChoiceMadeText(String chosenLabel){
        //return YOU_CHOSED.replace("{buttonLabel}", chosenLabel);
        return YOU_CHOSED + chosenLabel + ".";
    }


    @Override
    public String getBotUsername() { //FIXME clean up this method
        return null;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
