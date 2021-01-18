package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.REMOVE_WORD;

@Component
public class DeletingWord extends MessageBuilder {

    private DictionaryService dictionaryService;

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public String getResponseMessageText(String messageText) {
        if (messageText.equals(REMOVE_WORD)) {
            return "Введіть слово англійською";
        }
        if (!messageText.matches("^[a-zA-Z]+$")) {
            return "Помилка в написанні слова англійською, воно має бути написано тільки латиницею.";
        }
        Word wordInDictionary = dictionaryService.getWordByEnglishValue(messageText);
        if (wordInDictionary == null) {
            return "Слово " + messageText + " відсутнє у Вашому словнику.";
        }
        dictionaryService.deleteWord(wordInDictionary);
        return "Слово " + messageText + " було видалено словника.";
    }
}

