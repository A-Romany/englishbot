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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.agbrothers.englishbot.constant.CommonPhrase.MAKE_CHOICE;

@Component
public class PrintWordsDictionaryProcessor implements Processor {

    private DictionaryService dictionaryService;

    @Override
    public void process(ProcessingExchange exchange) {
        List<Word> allWordsSorted = getAllWordsSorted();

        if (allWordsSorted.size() < 13) {
            exchange.appendResponseMessageText(printWords(allWordsSorted));
        } else {
            String messageText = exchange.getMessageText();
            exchange.appendResponseMessageText(getResponseMessageText(allWordsSorted, messageText));
            exchange.getResponseMessages().get(0).setResponseButtons(getKeyboardButtons(getWordListsByFirstWord(allWordsSorted)));
        }
        exchange.setExchangeState(State.READY_TO_SEND);
    }

    private String getResponseMessageText(List<Word> list, String messageText) {
        String result = MAKE_CHOICE;
        Map<String, List<Word>> wordListsByFirstWord = getWordListsByFirstWord(list);
        if ((list.size() > 12) && (!wordListsByFirstWord.containsKey(messageText))) {
            result = CommonPhrase.SELECT_RANGE;
        } else if (wordListsByFirstWord.containsKey(messageText)) {
            result = printWords(wordListsByFirstWord.get(messageText));
        }
        return result;
    }

    private Map<String, List<Word>> getWordListsByFirstWord(List<Word> allWordsSorted) {
        Map<String, List<Word>> result = new LinkedHashMap<>();
        int dozensCount = allWordsSorted.size() % 10 < 3
                ? allWordsSorted.size() / 10
                : allWordsSorted.size() / 10 + 1;

        for (int i = 0; i < dozensCount; i++) {
            int firstWordIndex = i * 10; //currentDozen
            if (i + 1 == dozensCount) {
                result.put(allWordsSorted.get(firstWordIndex).getEnglishValue(), allWordsSorted.subList(firstWordIndex, allWordsSorted.size()));
            } else {
                result.put(allWordsSorted.get(firstWordIndex).getEnglishValue(), allWordsSorted.subList(firstWordIndex, firstWordIndex + 10));
            }
        }
        return result;
    }

    private List<Map<String, String>> getKeyboardButtons(Map<String, List<Word>> wordListsByFirstWord) {

        List<Map<String, String>> resultMaps = new ArrayList<>();
        Map<String, String> resultMap = new LinkedHashMap<>();

        for (Map.Entry<String, List<Word>> entry : wordListsByFirstWord.entrySet()) {
            String firstWordOfDozen = entry.getKey();
            String lastWordOfDozen = entry.getValue().get(entry.getValue().size() - 1).getEnglishValue();
            resultMap.put(firstWordOfDozen, firstWordOfDozen + StringPart.HYPHEN + lastWordOfDozen);
        }
        resultMaps.add(resultMap);

        return resultMaps;
    }

    private List<Word> getAllWordsSorted() {
        List<Word> allWords = dictionaryService.getAllWords();

        allWords.sort(Comparator.comparing(Word::getEnglishValue));
        return allWords;
    }

    private String printWords(List<Word> list) {
        StringBuilder printWords = new StringBuilder();
        list.forEach(w -> printWords.append(w.getEnglishValue()).append(StringPart.HYPHEN).
                append(w.getUkrainianValue()).append(StringPart.NEWLINE));
        return printWords.toString();
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}