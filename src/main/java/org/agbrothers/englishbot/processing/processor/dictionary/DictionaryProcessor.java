package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.constant.State;
import org.agbrothers.englishbot.messagebuilder.DictionaryMessageBuilder;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.processing.processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryProcessor implements Processor {

    private DictionaryMessageBuilder dictionaryMessageBuilder;

    @Override
    public void process(ProcessingExchange exchange) {
        String responseMessageText = dictionaryMessageBuilder.getResponseMessageText(exchange.getMessageText());
        exchange.appendResponseMessageText(responseMessageText);
        exchange.setExchangeState(State.READY_TO_SEND);
    }

    @Autowired
    public void setDictionaryMessageBuilder(DictionaryMessageBuilder dictionaryMessageBuilder) {
        this.dictionaryMessageBuilder = dictionaryMessageBuilder;
    }
}
