package org.agbrothers.englishbot.buttonsbuilder;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public abstract class ButtonsBuilder {

    /**
     * Returns chat keyboard for a specified messageText.
     * Must return {@code null} if there is no keyboard for this messageText
     * @param messageText specified messageText
     * @return keyboard as {@code Map}
     */
    public abstract List<Map<String, String>> getKeyboardButtons(String messageText);

}