package org.agbrothers.englishbot.processing.processor;

import org.agbrothers.englishbot.constant.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessorFactory {

    private final Map<String, String> processorNameByState;
    private Map<String, Processor> processorByName;

    public ProcessorFactory() {
        processorNameByState = new HashMap<>();
        processorNameByState.put(State.MAIN_MENU, "mainMenuProcessor");
        processorNameByState.put(State.ENGLISH_LESSON, "englishLessonProcessor");
        processorNameByState.put(State.UKRAINIAN_LESSON, "ukrainianLessonProcessor");
        processorNameByState.put(State.DICTIONARY, "dictionaryProcessor");
        processorNameByState.put(State.ADD_WORD_TO_DICTIONARY, "addWordDictionaryProcessor");
        processorNameByState.put(State.DELETING_WORD, "deleteWordDictionaryProcessor");
        processorNameByState.put(State.PRINTING_WORDS, "printWordsDictionaryProcessor");
    }

    public Processor getProcessorByState(String state) {
        return processorByName.get(processorNameByState.get(state));
    }

    @Autowired
    public void setProcessorByName(Map<String, Processor> processorByName) {
        this.processorByName = processorByName;
    }
}
