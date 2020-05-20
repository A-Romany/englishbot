//import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.objects.Update;
//import org.telegram.telegrambots.exceptions.TelegramApiException;

import static constant.ButtonLabel.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import persistence.User;

import java.util.ArrayList;
import java.util.List;

public class EnglishBot extends TelegramLongPollingBot {

    private boolean countHello=false;

    private static final String HELLO_MESSAGE = "Привіт, я дуже радий, що ти хочеш покращити свої знання англійської мови!!!" +
            " Маю надію, що я тобі в цьому допоможу!))                    "+
            "Для початку навчання натисни "+MAIN_MENU;
    private static final String MAKE_CHOICE = "Будь-ласка зробіть свій вибір";
    private static final String RETURN_MAIN_MENU = "  Для повернення в головне меню натисніть "+ MAIN_MENU;
    //private static  final String MENU_DICTIONARY = "Ви вибрали \"Dictionary\". " + MAKE_CHOICE + RETURN_MAIN_MENU;
    //private static final String MENU_LESSONS ="Ви вибрали \"Lessons\". " + MAKE_CHOICE + RETURN_MAIN_MENU;;
    private static final String BOT_TOKEN = "1153088687:AAGlMcZ6W2qvE_YaWlYkXTkg9W2Yg8XZL0U";
    //private static final String YOU_CHOSED = "Ви вибрали {buttonLabel}.";
    private static final String YOU_CHOSED = "Ви вибрали ";

//    @Override
//    public BotApiMethod onWebhookUpdateReceived(Update update) {
//        onUpdateReceived(update);
//
//        return null;
//    }

    @Override
    public void onUpdateReceived(Update update) { //FIXME RF-001 veeeeeery long method, split it into several smaller and avoid code duplications
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        //First message //FIXME more informative comment please
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

//        if (messageText.equals(ButtonLabel.LESSONS.label)) {
//            SendMessage message = new SendMessage()
//                    .setChatId(chatId)
//                    .setText(getChoiceMadeText(ButtonLabel.LESSONS.label) + MAKE_CHOICE +
//                            RETURN_MAIN_MENU);
//            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//            List<KeyboardRow> keyboard = new ArrayList<>();
//            KeyboardRow row = new KeyboardRow();
//            row.add(ButtonLabel.ENGLISH.label);
//            row.add(ButtonLabel.UKRAINIAN.label);
//            // Add the first row to the keyboard
//            keyboard.add(row);
//            // Set the keyboard to the markup
//            keyboardMarkup.setKeyboard(keyboard);
//            // Add it to the message
//            message.setReplyMarkup(keyboardMarkup);
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//        else if (messageText.equals(ButtonLabel.DICTIONARY.label)) {
//            SendMessage message = new SendMessage()
//                    .setChatId(chatId)
//                    .setText(getChoiceMadeText(ButtonLabel.DICTIONARY.label) + MAKE_CHOICE +
//                            RETURN_MAIN_MENU);
//
//            // Create ReplyKeyboardMarkup object
//            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//            // Create the keyboard (list of keyboard rows)
//            List<KeyboardRow> keyboard = new ArrayList<>();
//            // Create a keyboard row
//            KeyboardRow row = new KeyboardRow();
//            // Set each button, you can also use KeyboardButton objects if you need something else than text
//            row.add(ButtonLabel.PRINT_ALL_WORD.label);
//            row.add(ButtonLabel.ADD_WORD.label);
//            // Add the first row to the keyboard
//            keyboard.add(row);
//            row = new KeyboardRow();
//            // Set each button, you can also use KeyboardButton objects if you need something else than text
//            row.add(ButtonLabel.EDIT_DICTIONARY.label);
//            row.add(ButtonLabel.REMOVE_WORD.label);
//            // Add the second row to the keyboard
//            keyboard.add(row);
//            // Set the keyboard to the markup
//            keyboardMarkup.setKeyboard(keyboard);
//            // Add it to the message
//            message.setReplyMarkup(keyboardMarkup);
//            try {
//                execute(message); // Sending our message object to user
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//        else if (messageText.equals(ButtonLabel.MAIN_MENU.label)) {
//            SendMessage message = new SendMessage() // Create a message object object
//                    .setChatId(chatId)
//                    .setText(MAKE_CHOICE);
//            // Create ReplyKeyboardMarkup object
//            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//            // Create the keyboard (list of keyboard rows)
//            List<KeyboardRow> keyboard = new ArrayList<>();
//
//            KeyboardRow row = new KeyboardRow();
//            row.add(ButtonLabel.DICTIONARY.label);
//            row.add(ButtonLabel.LESSONS.label);
//            keyboard.add(row);
//            keyboardMarkup.setKeyboard(keyboard);
//            message.setReplyMarkup(keyboardMarkup);
//            try {
//                execute(message); // Sending our message object to user
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
        methodForSwitch(messageText, chatId);




    }


    @Override
    public String getBotUsername() { //FIXME clean up this method
        return null;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void methodForSwitch(String label, long chatId){
        switch ( label) {
            case LESSONS:
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText(getChoiceMadeText(LESSONS) + MAKE_CHOICE +
                                RETURN_MAIN_MENU);

//                            setText(getChoiceMadeText(ButtonLabel.LESSONS.label) + MAKE_CHOICE +
//                            RETURN_MAIN_MENU);
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add(ENGLISH);
                row.add(UKRAINIAN);
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case DICTIONARY:
                message = new SendMessage()
                        .setChatId(chatId)
                        .setText(getChoiceMadeText(DICTIONARY) + MAKE_CHOICE +
                                RETURN_MAIN_MENU);

                keyboardMarkup = new ReplyKeyboardMarkup();

                keyboard = new ArrayList<>();

                row = new KeyboardRow();

                row.add(PRINT_ALL_WORD);
                row.add(ADD_WORD);

                keyboard.add(row);
                row = new KeyboardRow();

                row.add(EDIT_DICTIONARY);
                row.add(REMOVE_WORD);

                keyboard.add(row);

                keyboardMarkup.setKeyboard(keyboard);

                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case MAIN_MENU:
                message = new SendMessage()
                        .setChatId(chatId)
                        .setText(MAKE_CHOICE);
                keyboardMarkup = new ReplyKeyboardMarkup();
                keyboard = new ArrayList<>();

                row = new KeyboardRow();
                row.add(DICTIONARY);
                row.add(LESSONS);
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case REMOVE_WORD:
                message = new SendMessage()
                        .setChatId(chatId)
                        .setText(getChoiceMadeText(REMOVE_WORD) + " Триває розробка видалення слів зі словника" +
                                RETURN_MAIN_MENU);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case EDIT_DICTIONARY:

                message = new SendMessage()
                        .setChatId(chatId)
                        .setText(getChoiceMadeText(EDIT_DICTIONARY) + " Триває розробка редагування слів зі словника" +
                                RETURN_MAIN_MENU);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case PRINT_ALL_WORD:
                message = new SendMessage()
                        .setChatId(chatId)
                        .setText(getChoiceMadeText(PRINT_ALL_WORD) + " Триває розробка друку слів зі словника" +
                                RETURN_MAIN_MENU);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case ADD_WORD:
                message = new SendMessage()
                        .setChatId(chatId)
                        .setText(getChoiceMadeText(ADD_WORD) + " Триває розробка додавання слів до словника" +
                    RETURN_MAIN_MENU);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            break;


        }
    }

    public void addPerson(User person){
        Transaction tx = null;
        try (Session session = buildSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.save(person);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    private static SessionFactory buildSessionFactory(){
        return  new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    private String getChoiceMadeText(String chosenLabel){
        //return YOU_CHOSED.replace("{buttonLabel}", chosenLabel);
        return YOU_CHOSED + chosenLabel + ".";
    }


}
