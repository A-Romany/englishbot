import org.telegram.telegrambots.ApiContextInitializer;
//import org.telegram.telegrambots.TelegramBotsApi;
//import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Registration bot
        try {
            EnglishBot bot = new EnglishBot();
            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
