package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.constant.CommonPhrase;
import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.constant.StringPart;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.ADD_WORD;
import static org.agbrothers.englishbot.constant.CommonPhrase.TYPED_WORD_ERROR;

@Component
public class AddWordDictionaryProcessor implements Processor {
    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange) {
        String messageText = exchange.getMessageText();
        if (messageText.equals(ADD_WORD)) {
            exchange.appendResponseMessageText(CommonPhrase.ENTER_ENGLISH_UKRAINIAN_WORD);
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }

        messageText = messageText.trim().replaceAll("[ ]{2,}", " ");

        exchange.setMessageText(messageText);

        String[] wordData = messageText.split(StringPart.SPACE);
        String errorMessage = validateWordData(wordData);
        if (errorMessage != null) {
            exchange.appendResponseMessageText(errorMessage);
            exchange.setExchangeState(State.READY_TO_SEND);
            return;
        }
        String englishValue = wordData[0].toLowerCase();
        String ukrainianValue = wordData[1].toLowerCase();
        dictionaryService.addWord(new Word(englishValue, ukrainianValue));
        exchange.appendResponseMessageText(String.format (CommonPhrase.WORD_ADDED, englishValue, ukrainianValue));
        exchange.setExchangeState(State.READY_TO_SEND);
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * Validate word data input
     * @return validation error message, {@code null} if validation succeeded
     */
    private String validateWordData(String[] wordData) {
        if (isValidWordData(wordData)) {
            return TYPED_WORD_ERROR;
        }
        Word wordInDictionary = dictionaryService.getWordByEnglishValue(wordData[0].toLowerCase());
        if (wordInDictionary != null) {
            return String.format(CommonPhrase.WORD_EXISTS_IN_VOCABULARY, wordInDictionary.getEnglishValue(), wordInDictionary.getUkrainianValue());
        }
        return null;
    }

    private boolean isValidWordData(String[] wordData) {
        return wordData.length != 2
                || !wordData[0].matches("^[a-zA-Z]+$")
                || !wordData[1].matches("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії'`’ʼ]+$");
    }
}
