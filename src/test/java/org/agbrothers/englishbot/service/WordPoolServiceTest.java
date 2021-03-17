package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class WordPoolServiceTest {

    private final WordRepository wordRepository = mock(WordRepository.class);
    private final WordPoolService wordPoolService = new WordPoolService(wordRepository);

    @Test
    void getRandomWordPool() {
        List<Long> wordIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L);

        when(wordRepository.getWordIds()).thenReturn(wordIds);
        when(wordRepository.findWordsByIdIn(anyList())).thenReturn(Collections.emptyList());

        wordPoolService.getRandomWordPool();
        ArgumentCaptor<List<Long>> captor = ArgumentCaptor.forClass(List.class);
        verify(wordRepository).findWordsByIdIn(captor.capture());
        assertTrue(wordIds.containsAll(captor.getValue()));

        verify(wordRepository).getWordIds();
        verify(wordRepository).findWordsByIdIn(anyList());
    }
}