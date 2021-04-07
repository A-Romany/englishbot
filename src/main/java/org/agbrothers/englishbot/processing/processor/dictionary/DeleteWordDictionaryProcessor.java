package org.agbrothers.englishbot.processing.processor.dictionary;

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
            exchange.appendResponseMessageText("Введіть слово англійською");
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        if (!messageText.matches("^[a-zA-Z]+$")) {
            exchange.appendResponseMessageText("Помилка в написанні слова англійською," +
                    " воно має бути написано тільки латиницею.");
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        Word wordInDictionary = dictionaryService.getWordByEnglishValue(messageText.toLowerCase());
        if (wordInDictionary == null) {
            exchange.appendResponseMessageText("Слово " + messageText.toLowerCase() + " відсутнє у Вашому словнику.");
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        dictionaryService.deleteWord(wordInDictionary);
        exchange.appendResponseMessageText("Слово " + wordInDictionary.getEnglishValue() + " було видалено словника.");
        exchange.setExchangeState(State.READY_TO_SEND);
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}
