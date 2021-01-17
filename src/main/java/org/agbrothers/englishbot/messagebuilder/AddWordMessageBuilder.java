package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;

@Component
public class AddWordMessageBuilder extends MessageBuilder{
    private DictionaryService dictionaryService;

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public String getResponseMessageText(String messageText) {
        if (messageText.equals(ADD_WORD)){
            return "Введіть слово англійською та його переклад через пробіл";
        }
        String[] wordData = messageText.split(" ");
        if(wordData.length!=2 || !wordData[0].matches("^[a-zA-Z]+$") || !wordData[1].matches("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії'`’ʼ]+$")){
            return "Помилка в слові англійською або перекладі слова. Слово англійською має бути латиницею, переклад - лише кирилицею.";
        }
        Word wordInDictionary = dictionaryService.getWordFromRepository(wordData[0]);
        if(null!=wordInDictionary) {
            return "Слово " + wordInDictionary.getEnglishValue() + " - " + wordInDictionary.getUkrainianValue() + " вже існує у Вашому словнику.";
        }
        dictionaryService.addWord(new Word(wordData[0],wordData[1]));
        return "Слово " + wordData[0] +  " - " + wordData[1] + " було додано до словника.";
    }
}
