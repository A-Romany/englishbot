package org.agbrothers.englishbot.adapter.telegram.receiver;

import org.agbrothers.englishbot.process.ProcessingCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.api.objects.Update;

@RestController
public class TelegramHttpController {

    private final ProcessingCore processingCore;

    @Autowired
    public TelegramHttpController(ProcessingCore processingCore) {
        this.processingCore = processingCore;
    }

    /**
     * This method parses telegram messages.
     *
     * @param update
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void onUpdateReceived(@RequestBody Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            processingCore.processMessage(chatId, messageText);
        }
    }

    /**
     * Don't bother. It's just some kind of health check or whatever.
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String hello(){
        return "pong";
    }

}
