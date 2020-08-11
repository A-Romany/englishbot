package org.agbrothers.englishbot.adapter.telegram.receiver;

import org.agbrothers.englishbot.process.ProcessingCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

@RestController
public class TelegramHttpController {

    private final ProcessingCore processingCore;
    private final Set<String> chatRegistry = new HashSet<>();

    @Autowired
    public TelegramHttpController(ProcessingCore processingCore) {
        this.processingCore = processingCore;
    }

    /**
     * This method parses telegram messages.
     *
     * @param update received message from Telegram channel
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void onUpdateReceived(@RequestBody Update update) {
        String messageText = null;
        String chatId = null;

        if(update.hasMessage() && update.getMessage().hasText() ) {
            messageText = update.getMessage().getText();
            chatId = String.valueOf(update.getMessage().getChatId());
        } else if(update.hasCallbackQuery()) {
            messageText = update.getCallbackQuery().getData();
            chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        }

        if(messageText==null || chatId == null) {
            return;
        }

        processingCore.processMessage(chatId, messageText);
    }

    /**
     * Don't bother. It's just some kind of health check or whatever.
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String hello(){
        return "pong";
    }

}
