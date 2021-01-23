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
        List<Word> allSortWords = getSortAllWords(dictionaryService.getAllWords()); //FIXME the govnocode :)

        exchange.setResponseMessageText(getResponseMessageText(allSortWords, messageText));
        if(allSortWords.size()>=12){
            exchange.setResponseButtons(getKeyboardButtons(allSortWords));
        }
    }

    private String getResponseMessageText (List<Word> list, String messageText){
        Word currentWord = getCurrentFirstWordInDozen(list, messageText);

        if(list.size()<13){
            return printDozenWords(list);
        }
        else {
            if (messageText.equals(currentWord.getEnglishValue())) {
                List<Word> subSortWords = getSubListWords(list, list.indexOf(currentWord));
                return printDozenWords(subSortWords);
            } else {
                return "У словнику більше 10 слів. Будь ласка вибиріть діапозон слів для перегляду:";
            }
        }
    }

    private Map<String, String> getKeyboardButtons(List<Word> list) {
        int dozens = list.size()%10==0 ? list.size()/10 : list.size()/10 + 1;
        Map<String, String> dozensWords =  new HashMap<>();
        String firstWordOfDozen;
        String lastWordOfDozen;
        String valueDozensWords;
        for (int i = 0; i < dozens; i++) {
            firstWordOfDozen = list.get(i*10).getEnglishValue();
            if(i==dozens-1) {
                 lastWordOfDozen = list.get(i*10+list.size()%10-1).getEnglishValue();
                 if(list.size()%10==1||list.size()%10==2) {
                     firstWordOfDozen = list.get(i * 10 - 10).getEnglishValue();
                 }
            }
            else {
                lastWordOfDozen = list.get(i * 10 + 9).getEnglishValue();
            }
            valueDozensWords = firstWordOfDozen + " - " + lastWordOfDozen;
            dozensWords.put(firstWordOfDozen, valueDozensWords);
        }
        return dozensWords;
    }

    private List<Word> getSortAllWords (List<Word> words){
        List<Word> allWords = new ArrayList<>(words);
        List<Word> allSortWords = new ArrayList<>();
        List<String> englishWords = new ArrayList<>();

        for(Word word : allWords){
            englishWords.add(word.getEnglishValue());
        }

        Collections.sort(englishWords);

        for (String englishWord : englishWords) {
            for (Word allWord : allWords) {
                if (englishWord.equals(allWord.getEnglishValue())) {
                    allSortWords.add(allWord);
                    break;
                }
            }
        }
        return  allSortWords;
    }

    private List<Word> getSubListWords (List<Word> list, int index){//TODO var count word to print
        if ((list.size() - index)==1){
            return list.subList(index,list.size()-1);
        }
        else if((list.size() - index)<13){
            return list.subList(index, list.size());
        }
        return list.subList(index, (index+10));
    }

    private Word getCurrentFirstWordInDozen (List<Word> list, String messageText){
        List<Word> englishValueForFirstWordOfDozen = new ArrayList<>();
        Word currentWord = new Word();
        for (int i = 0; i < list.size(); i++) {
            if(i%10==0){
                englishValueForFirstWordOfDozen.add(list.get(i));
            }
        }
        for(Word word : englishValueForFirstWordOfDozen){
            if(messageText.equals(word.getEnglishValue())){
                currentWord = word;
            }
        }
        return currentWord;
    }

    private String printDozenWords(List<Word> list){
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