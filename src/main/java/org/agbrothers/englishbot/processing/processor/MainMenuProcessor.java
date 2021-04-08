package org.agbrothers.englishbot.processing.processor;

import org.agbrothers.englishbot.buttonsbuilder.MainMenuButtonsBuilder;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.messagebuilder.MainMenuMessageBuilder;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MainMenuProcessor implements Processor {

    private MainMenuMessageBuilder mainMenuMessageBuilder;
    private MainMenuButtonsBuilder mainMenuButtonsBuilder;
    private WordRepository wordRepository;

    @Override
    public void process(ProcessingExchange exchange) {
        if (exchange.getMessageText().equals(ButtonLabel.LESSONS) && (getNumberOfWord() < 10)) {
            int numberOfWord = getNumberOfWord();

            exchange.appendResponseMessageText("У Вас в словнику менше 10 слів. Для проходження уроку необхідно додати "
                    + (10 - numberOfWord) + " слів. Зараз у Вашому словнику знаходяться наступні слова:\n");
            exchange.setExchangeState(State.PRINTING_WORDS);
            List<Map<String, String>> keyboardButtons = mainMenuButtonsBuilder.getKeyboardButtons(State.DICTIONARY);
            exchange.setResponseButtons(keyboardButtons);
        } else {
            String requestMessageText = exchange.getMessageText();
            String responseMessageText = mainMenuMessageBuilder.getResponseMessageText(requestMessageText);
            exchange.appendResponseMessageText(responseMessageText);

            List<Map<String, String>> keyboardButtons = mainMenuButtonsBuilder.getKeyboardButtons(requestMessageText);
            exchange.setResponseButtons(keyboardButtons);
            exchange.setExchangeState(State.READY_TO_SEND);
        }
    }

    @Autowired
    public void setMainMenuMessageBuilder(MainMenuMessageBuilder mainMenuMessageBuilder) {
        this.mainMenuMessageBuilder = mainMenuMessageBuilder;
    }

    @Autowired
    public void setMainMenuButtonsBuilder(MainMenuButtonsBuilder mainMenuButtonsBuilder) {
        this.mainMenuButtonsBuilder = mainMenuButtonsBuilder;
    }

    private int getNumberOfWord() {
        List<Long> wordIds = wordRepository.getWordIds();
        return wordIds.size();
    }

    @Autowired
    public void setWordRepository(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }
}
