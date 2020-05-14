package service;

import persistence.EnglishBotDAO;

public class EnglishBotService {
    private EnglishBotDAO englishBotDAO;

    public EnglishBotService(EnglishBotDAO englishBotDAO) {
        this.englishBotDAO = englishBotDAO;
    }
}
