package org.agbrothers.englishbot.buttonsbuilder;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PrintingWordsButtonBuilder extends ButtonsBuilder{
    private DictionaryService dictionaryService;

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Map<String, String> getKeyboardButtons(String messageText) {
        List<Word> allWords = new ArrayList<>(dictionaryService.getAllWords());
        List<Word> allWordsToPrint = new ArrayList<>();
        int size = allWords.size();
        List<String> englishWords = new ArrayList<>();
        for(Word words : allWords){
            englishWords.add(words.getEnglishValue());
        }
        Collections.sort(englishWords);

        for (String englishWord : englishWords) {
            for (Word allWord : allWords) {
                if (englishWord.equals(allWord.getEnglishValue())) {
                    allWordsToPrint.add(allWord);
                    break;
                }
            }

        }

        int countDozen = (size%10==0) ? size%10 : size%10+1;
        String sizeS = "" + size;
        Map<String, String> keyboardMap =  new HashMap<>();

        if(size<13) {
            return null;
        }
        for (int i = 0; i < countDozen; i++) {
            //keyboardMap.put(i,)
        }
//        for (Word answer : answers) {
//            keyboardMap.put(answer.getEnglishValue(), answer.getEnglishValue());
//        }
        return keyboardMap;
    }
}
