package org.agbrothers.englishbot.processing.processor.dictionary;

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
            exchange.setResponseMessageText("Введіть слово англійською");
            return;
        }
        if (!messageText.matches("^[a-zA-Z]+$")) {
            exchange.setResponseMessageText("Помилка в написанні слова англійською," +
                    " воно має бути написано тільки латиницею.");
            return;
        }
        Word wordInDictionary = dictionaryService.getWordByEnglishValue(messageText);
        if (wordInDictionary == null) {
            exchange.setResponseMessageText("Слово " + messageText + " відсутнє у Вашому словнику.");
            return;
        }
        dictionaryService.deleteWord(wordInDictionary);
        exchange.setResponseMessageText("Слово " + messageText + " було видалено словника.");
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}
