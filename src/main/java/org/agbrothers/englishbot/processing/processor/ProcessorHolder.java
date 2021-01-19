package org.agbrothers.englishbot.processing.processor;

import org.agbrothers.englishbot.constant.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessorHolder {

    private final Map<String, String> processorNameByState;
    private Map<String, Processor> processorByName;

    public ProcessorHolder() {
        processorNameByState = new HashMap<>();
        processorNameByState.put(State.MAIN_MENU, "mainMenuProcessor");
        processorNameByState.put(State.ENGLISH_LESSON, "englishLessonProcessor");
        processorNameByState.put(State.UKRAINIAN_LESSON, "ukrainianLessonProcessor");
    }

    public Processor getProcessorByState(String state) {
        return processorByName.get(processorNameByState.get(state));
    }

    @Autowired
    public void setProcessorByName(Map<String, Processor> processorByName) {
        this.processorByName = processorByName;
    }
}
