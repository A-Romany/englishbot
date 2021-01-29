package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.agbrothers.englishbot.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.agbrothers.englishbot.constant.CommonPhrase.RETURN_MAIN_MENU;
import static org.agbrothers.englishbot.constant.MessageLabel.MAKE_CHOICE;

@Component
public class PrintWordsDictionaryProcessor implements Processor {

    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange) {
        List<Word> allWordsSorted = getAllWordsSorted();

        if (allWordsSorted.size() < 13){
            exchange.setResponseMessageText(printWords(allWordsSorted) + "\n" + RETURN_MAIN_MENU);
        }
        else {
            String messageText = exchange.getMessageText();
            exchange.setResponseMessageText(getResponseMessageText(allWordsSorted, messageText));
            exchange.setResponseButtons(getKeyboardButtons(getWordListsByFirstWord( allWordsSorted)));
        }
    }

    private String getResponseMessageText(List<Word> list, String messageText) {
        String result = MAKE_CHOICE;
        Map<String, List<Word>> wordListsByFirstWord = getWordListsByFirstWord(list);
        if ((list.size() > 12) && (!wordListsByFirstWord.containsKey(messageText))) {
            result = "У словнику більше 10 слів. Будь ласка виберіть діапазон слів для перегляду:";
        }
        else if (wordListsByFirstWord.containsKey(messageText)) {
            result = printWords(wordListsByFirstWord.get(messageText)) + "\n" + RETURN_MAIN_MENU;
        }
        return result;
    }

    private Map<String, List<Word>> getWordListsByFirstWord(List<Word> allWordsSorted) {
        Map<String, List<Word>> result = new LinkedHashMap<>();
        int dozensCount = allWordsSorted.size() % 10 < 3
                ? allWordsSorted.size()/10
                : allWordsSorted.size()/10 + 1;

        for (int i = 0; i<dozensCount; i++) {
            int firstWordIndex = i*10; //currentDozen
            if (i + 1 == dozensCount) {
                result.put(allWordsSorted.get(firstWordIndex).getEnglishValue(), allWordsSorted.subList(firstWordIndex,allWordsSorted.size()));
            }
            else {
                result.put(allWordsSorted.get(firstWordIndex).getEnglishValue(), allWordsSorted.subList(firstWordIndex, firstWordIndex + 10));
            }
        }
        return result;
    }

    private Map<String, String> getKeyboardButtons(Map<String, List<Word>> wordListsByFirstWord) {
        Map<String, String> result =  new LinkedHashMap<>();

        for (Map.Entry<String, List<Word>> entry : wordListsByFirstWord.entrySet()) {
            String firstWordOfDozen = entry.getKey();
            String lastWordOfDozen = entry.getValue().get(entry.getValue().size()-1).getEnglishValue();
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
    public void  setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}