package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.REMOVE_WORD;

@Component
public class DeleteWordDictionaryProcessor implements Processor {

    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange) {
        String messageText = exchange.getMessageText();
        if (messageText.equals(REMOVE_WORD)) {
            exchange.appendResponseMessageText(CommonPhrase.ENTER_ENGLISH_WORD);
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        if (!messageText.matches("^[a-zA-Z]+$")) {
            exchange.appendResponseMessageText(CommonPhrase.ERROR_ENTERING_WORD);
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        Word wordInDictionary = dictionaryService.getWordByEnglishValue(messageText.toLowerCase());
        if (wordInDictionary == null) {
            exchange.appendResponseMessageText(String.format(CommonPhrase.WORD_NOT_EXISTS_IN_VOCABULARY, messageText.toLowerCase()));
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        dictionaryService.deleteWord(wordInDictionary);
        exchange.appendResponseMessageText(String.format(CommonPhrase.WORD_DELETED, wordInDictionary.getEnglishValue()));
        exchange.setExchangeState(State.READY_TO_SEND);
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}
