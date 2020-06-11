import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static constant.ButtonLabel.*;

public class EnglishBot extends TelegramLongPollingBot {

    private final Set<Long> chatRegistry = new HashSet<>();//TODO set

    private static final String HELLO_MESSAGE = "Привіт, я дуже радий, що ти хочеш покращити свої знання англійської мови!!!" +
            " Маю надію, що я тобі в цьому допоможу!))"+
            "Для початку навчання натисни "+MAIN_MENU;
    private static final String MAKE_CHOICE = "Будь-ласка зробіть свій вибір";
    private static final String RETURN_MAIN_MENU = "  Для повернення в головне меню натисніть "+ MAIN_MENU;
    private static final String BOT_TOKEN = "1153088687:AAGlMcZ6W2qvE_YaWlYkXTkg9W2Yg8XZL0U";
    private static final String YOU_CHOOSE = "Ви вибрали ";


    @Override
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if(chatRegistry.add(chatId)){
            sendHelloMessage(chatId);
        }

        SendMessage message = new SendMessage().setChatId(chatId);
        String responseMessageText = getResponseMessageText(messageText);
        message.setText(responseMessageText);

        ReplyKeyboardMarkup responseKeyboardMarkup = getResponseKeyboardMarkup(messageText);
        message.setReplyMarkup(responseKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendHelloMessage(Long chatId){
        SendMessage helloMessage = new SendMessage()
                .setChatId(chatId)
                .setText(HELLO_MESSAGE);
        try {
            execute(helloMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

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

    public ReplyKeyboardMarkup getResponseKeyboardMarkup(String label){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard;
        KeyboardRow row;

        switch ( label) {
            case LESSONS:
                keyboard = new ArrayList<>();
                row = new KeyboardRow();
                row.add(ENGLISH);
                row.add(UKRAINIAN);
                keyboard.add(row);
                return keyboardMarkup.setKeyboard(keyboard);

            case DICTIONARY:
                keyboard = new ArrayList<>();
                row = new KeyboardRow();
                row.add(PRINT_ALL_WORD);
                row.add(ADD_WORD);
                keyboard.add(row);
                row = new KeyboardRow();
                row.add(EDIT_DICTIONARY);
                row.add(REMOVE_WORD);
                keyboard.add(row);
                return keyboardMarkup.setKeyboard(keyboard);
            case MAIN_MENU:
                keyboard = new ArrayList<>();
                row = new KeyboardRow();
                row.add(DICTIONARY);
                row.add(LESSONS);
                keyboard.add(row);
                return keyboardMarkup.setKeyboard(keyboard);
            default:
                return null;
        }



    }





    private String getChoiceMadeText(String chosenLabel){
        return YOU_CHOOSE + chosenLabel + ".";
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
