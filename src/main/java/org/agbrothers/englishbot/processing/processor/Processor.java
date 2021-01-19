package org.agbrothers.englishbot.processing.processor;

import org.agbrothers.englishbot.processing.ProcessingException;
import org.agbrothers.englishbot.processing.ProcessingExchange;

public interface Processor {
    void process(ProcessingExchange exchange) throws ProcessingException;
}
