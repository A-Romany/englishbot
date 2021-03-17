package org.agbrothers.englishbot.processing.processor.dictionary;

import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.entity.Word;
import org.agbrothers.englishbot.processing.ProcessingExchange;
import org.agbrothers.englishbot.service.DictionaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.agbrothers.englishbot.constant.ButtonLabel.ADD_WORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddWordDictionaryProcessorTest {

    private final DictionaryService dictionaryService = mock(DictionaryService.class);
    private final AddWordDictionaryProcessor addWordDictionaryProcessor = new AddWordDictionaryProcessor();

    private User user;

    @BeforeEach
    void init() {
        addWordDictionaryProcessor.setDictionaryService(dictionaryService);
        user = new User();
    }

    @Test
    void process_messageTextEqualsAddWord_EnterWord() {
        ProcessingExchange exchange = new ProcessingExchange(user, ADD_WORD);

        addWordDictionaryProcessor.process(exchange);

        String result = exchange.getResponseMessageText();
        assertEquals("Введіть слово англійською та його переклад через пробіл", result);
        verify(dictionaryService, never()).addWord(any(Word.class));
    }

    @Test
    void process_receiveWordAndTranslation_AddingWord() {
        ProcessingExchange exchange1 = new ProcessingExchange(user, "sea море");
        ProcessingExchange exchange2 = new ProcessingExchange(user, "  fly   муха ");

        when(dictionaryService.getWordByEnglishValue("sea")).thenReturn(null);

        addWordDictionaryProcessor.process(exchange1);

        String result1 = exchange1.getResponseMessageText();
        assertEquals("Слово sea - море було додано до словника.", result1);

        ArgumentCaptor<Word> captor = ArgumentCaptor.forClass(Word.class);
        verify(dictionaryService).addWord(captor.capture());
        assertEquals("sea", captor.getValue().getEnglishValue());

        addWordDictionaryProcessor.process(exchange2);
        String result2 = exchange2.getResponseMessageText();
        assertEquals("Слово fly - муха було додано до словника.", result2);

        ArgumentCaptor<Word> captor1 = ArgumentCaptor.forClass(Word.class);
        verify(dictionaryService, times(2)).addWord(captor1.capture());
        assertEquals("fly", captor1.getValue().getEnglishValue());

        verify(dictionaryService, times(2)).addWord(any(Word.class));
    }

    @Test
    void process_messageTextValidatedNewWord_NotAddingWord() {
        ProcessingExchange exchange = new ProcessingExchange(user, "sea море");

        when(dictionaryService.getWordByEnglishValue("sea")).thenReturn(new Word("sea", "море"));

        addWordDictionaryProcessor.process(exchange);

        String result = exchange.getResponseMessageText();
        assertEquals("Слово sea - море вже існує у Вашому словнику.", result);
        verify(dictionaryService, never()).addWord(any(Word.class));
    }

    @Test
    void process_messageTextValidatedNewWord_NotValidatedWord() {
        ProcessingExchange exchange1 = new ProcessingExchange(user, "se4a море");
        ProcessingExchange exchange2 = new ProcessingExchange(user, "море sea");
        ProcessingExchange exchange3 = new ProcessingExchange(user, "sea мор7е");

        addWordDictionaryProcessor.process(exchange1);
        addWordDictionaryProcessor.process(exchange2);
        addWordDictionaryProcessor.process(exchange3);

        String result1 = exchange1.getResponseMessageText();
        assertEquals("Помилка в слові англійською або перекладі слова. Слово англійською має бути латиницею, переклад - лише кирилицею.", result1);

        String result2 = exchange2.getResponseMessageText();
        assertEquals("Помилка в слові англійською або перекладі слова. Слово англійською має бути латиницею, переклад - лише кирилицею.", result2);

        String result3 = exchange3.getResponseMessageText();
        assertEquals("Помилка в слові англійською або перекладі слова. Слово англійською має бути латиницею, переклад - лише кирилицею.", result3);

        verify(dictionaryService, never()).addWord(any(Word.class));
    }
}