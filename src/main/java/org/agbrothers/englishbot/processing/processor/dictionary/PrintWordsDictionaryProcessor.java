package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingException;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.agbrothers.englishbot.constant.ButtonLabel.PRINT_ALL_WORD;

@Component
public class PrintWordsDictionaryProcessor implements Processor {

    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange)  {
        exchange.setResponseMessageText("printAllWords");
//        String messageText = exchange.getMessageText();
//        List<Word> allWords = new ArrayList<>(dictionaryService.getAllWords());
//        List<Word> allWordsToPrint = new ArrayList<>();
//        int size = allWords.size();
//        List<String> englishWords = new ArrayList<>();
//
//        if(size<33){
//            String printAllWords="";
//            for (Word word : dictionaryService.getAllWords()){
//                printAllWords += word.getEnglishValue() + " - " + word.getUkrainianValue() + "\n";
//            }
//            //exchange.setResponseMessageText(printAllWords);
//            exchange.setResponseMessageText("printAllWords");
//        }
//
//        for(Word words : allWords){
//            englishWords.add(words.getEnglishValue());
//        }
//        Collections.sort(englishWords);
//
//        for (String englishWord : englishWords) {
//            for (Word allWord : allWords) {
//                if (englishWord.equals(allWord.getEnglishValue())) {
//                    allWordsToPrint.add(allWord);
//                    break;
//                }
//            }
//
//        }

    }

    @Autowired
    public void  setDictionaryService(DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }
}
