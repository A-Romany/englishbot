package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.ButtonLabel;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.agbrothers.englishbot.constant.ButtonLabel.*;

@Component
public class PrintingWordsMessageBuilder extends MessageBuilder{
    private DictionaryService dictionaryService;

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }

    @Override
    public String getResponseMessageText(String messageText) {
        //if(messageText.equals(PRINT_ALL_WORD)){
            return "Як це блін працює????!!!!";
        //}
//        int size = dictionaryService.getAllWords().size();
//        if(size<13){
//            String printAllWords="";
//            for (Word word : dictionaryService.getAllWords()){
//                printAllWords += word.getEnglishValue() + " - " + word.getUkrainianValue() + "\n";
//            }
//            return printAllWords;
//        }
//        return "слів більше 12";
    }
}
