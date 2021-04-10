package org.agbrothers.englishbot.constant;


public final class CommonPhrase {
    private CommonPhrase() {}

    public static final String ERROR_MESSAGE = "Сталась прикра технічна помилка. " +
            "Спробуйте перейти в головне меню - " + LinkLabel.MAIN_MENU;
    public static final String RETURN_MAIN_MENU = "  Для повернення в головне меню натисніть " + LinkLabel.MAIN_MENU;
    public static final String TYPED_WORD_ERROR = "Помилка в слові англійською або перекладі слова. +" +
            "Слово англійською має бути латиницею, переклад - лише кирилицею.";
}
