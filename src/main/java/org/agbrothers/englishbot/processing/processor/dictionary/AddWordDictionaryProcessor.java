package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.ADD_WORD;

@Component
public class AddWordDictionaryProcessor implements Processor {
    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange) {
        String messageText = exchange.getMessageText();
        if (messageText.equals(ADD_WORD)) {
            exchange.setResponseMessageText("Введіть слово англійською та його переклад через пробіл");
            return;
        }

        messageText = messageText.trim().replaceAll("[ ]{2,}", " ");

        exchange.setMessageText(messageText);

        String[] wordData = messageText.split(" ");
        String errorMessage = validateWordData(wordData);
        if (errorMessage != null) {
            exchange.setResponseMessageText(errorMessage);
            return;
        }
        String englishValue = wordData[0].toLowerCase();
        String ukrainianValue = wordData[1].toLowerCase();
        dictionaryService.addWord(new Word(englishValue, ukrainianValue));
        exchange.setResponseMessageText("Слово " + englishValue + " - " + ukrainianValue + " було додано до словника.");
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
            return "Помилка в слові англійською або перекладі слова. " +
                    "Слово англійською має бути латиницею, переклад - лише кирилицею.";
        }
        Word wordInDictionary = dictionaryService.getWordByEnglishValue(wordData[0].toLowerCase());
        if (wordInDictionary != null) {
            return "Слово " + wordInDictionary.getEnglishValue() +
                    " - " + wordInDictionary.getUkrainianValue() + " вже існує у Вашому словнику.";
        }
        return null;
    }

    private boolean isValidWordData(String[] wordData) {
        return wordData.length != 2
                || !wordData[0].matches("^[a-zA-Z]+$")
                || !wordData[1].matches("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії'`’ʼ]+$");
    }
}
