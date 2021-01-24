package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PrintWordsDictionaryProcessor implements Processor {

    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange)  {
        String messageText = exchange.getMessageText();
        List<Word> allWordsSorted = getAllWordsSorted();

        if(allWordsSorted.size()<13){
            exchange.setResponseMessageText(printWords(allWordsSorted));
        }
        else        {
            exchange.setResponseMessageText(getResponseMessageText(allWordsSorted, messageText));
            exchange.setResponseButtons(getKeyboardButtons(allWordsSorted));
        }
    }

    private String getResponseMessageText (List<Word> list, String messageText) {
        String currentWordEngVal;
        if(getWordListsByFirstWord(list).containsKey(messageText)){
            return printWords(getWordListsByFirstWord(list).get(messageText));
        }
        else {
            return "У словнику більше 10 слів. Будь ласка виберіть діапазон слів для перегляду:";
        }
    }

    private Map<String, List<Word>> getWordListsByFirstWord(List<Word> allWordsSorted) {
        Map<String,List<Word>> result = new HashMap<>();
        int buttonsCount = allWordsSorted.size()%10 < 3
                ? allWordsSorted.size()/10
                : allWordsSorted.size()/10 + 1;

        for(int i = 0; i < buttonsCount; i++){
            if(i+1 == buttonsCount){
                result.put(allWordsSorted.get(i*10).getEnglishValue(), allWordsSorted.subList(i*10,allWordsSorted.size()));
            } else
            result.put(allWordsSorted.get(i*10).getEnglishValue(), allWordsSorted.subList(i*10,i*10+10));
        }
        return result;
    }

    private Map<String, String> getKeyboardButtons(List<Word> list) {
        int buttonsCount = list.size()%10 < 3
                ? list.size()/10
                : list.size()/10 + 1;
        Map<String, String> result =  new HashMap<>();

        for (int i = 0; i < buttonsCount; i++) {
            String firstWordOfDozen;
            String lastWordOfDozen;
            firstWordOfDozen = list.get(i*10).getEnglishValue();

            if(i + 1 == buttonsCount) { //last button
                 lastWordOfDozen = list.get(list.size()-1).getEnglishValue();
            }
            else {
                lastWordOfDozen = list.get(i * 10 + 9).getEnglishValue();
            }
            result.put(firstWordOfDozen, firstWordOfDozen + " - " + lastWordOfDozen);
        }
        return result;
    }

    private List<Word> getAllWordsSorted(){
        List<Word> allWords = dictionaryService.getAllWords();

        allWords.sort(Comparator.comparing(Word::getEnglishValue));
        return allWords;
    }


    private String printWords(List<Word> list){
        StringBuilder printWords = new StringBuilder();
        for (Word word : list){
            printWords.append(word.getEnglishValue()).append(" - ").
                    append(word.getUkrainianValue()).append("\n");
        }
        return printWords.toString();
    }

    @Autowired
    public void  setDictionaryService(DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }
}