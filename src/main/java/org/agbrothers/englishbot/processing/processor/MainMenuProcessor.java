package org.agbrothers.englishbot.processing.processor;

import org.agbrothers.englishbot.buttonsbuilder.MainMenuButtonsBuilder;
import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.Constant;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.constant.StringPart;
import org.agbrothers.englishbot.entity.ResponseMessage;
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
        if (exchange.getMessageText().equals(ButtonLabel.LESSONS) && (getNumberOfWord() < Constant.MIN_WORD_OF_WORDS)) {
            processNotEnoughWords(exchange);
        } else {
            ResponseMessage responseMessage = new ResponseMessage();

            String requestMessageText = exchange.getMessageText();

            List<Map<String, String>> keyboardButtons = mainMenuButtonsBuilder.getKeyboardButtons(requestMessageText);
            responseMessage.setResponseButtons(keyboardButtons);
            exchange.getResponseMessages().add(responseMessage);

            String responseMessageText = mainMenuMessageBuilder.getResponseMessageText(requestMessageText);
            exchange.appendResponseMessageText(responseMessageText);
            exchange.setExchangeState(State.READY_TO_SEND);
        }
    }

    private void processNotEnoughWords(ProcessingExchange exchange) {
        exchange.appendResponseMessageText(String.format(CommonPhrase.ADD_WORDS, (Constant.MIN_WORD_OF_WORDS - getNumberOfWord())) + StringPart.NEWLINE);
        exchange.setExchangeState(State.PRINTING_WORDS);
        List<Map<String, String>> keyboardButtons = mainMenuButtonsBuilder.getKeyboardButtons(State.DICTIONARY);

        exchange.getResponseMessages().get(0).setResponseButtons(keyboardButtons);
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
