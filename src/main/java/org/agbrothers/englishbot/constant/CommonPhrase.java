package org.agbrothers.englishbot.constant;


public final class CommonPhrase {
    private CommonPhrase() {}

    public static final String MAIN_MENU = "/main_menu";
    public static final String START = "/start";
    public static final String ERROR_MESSAGE = "Сталась прикра технічна помилка. " +
            "Спробуйте перейти в головне меню - " + MAIN_MENU;
    public static final String RETURN_TO_MAIN_MENU = "  Для повернення в головне меню натисніть " + MAIN_MENU;
    public static final String TYPED_WORD_ERROR = "Помилка в слові англійською або перекладі слова. Слово англійською має бути латиницею, переклад - лише кирилицею.";
    public static final String LESSON_ENDING = "Урок закінчено. Правильних відповідей - %d із %d.\n"; //count of words in lesson
    public static final String CORRECT_ANSWER = "Ви відповіли правильно! \n";
    public static final String INCORRECT_ANSWER = "Відповідь не вірна! Правильна відповідь - ";
    public static final String MAKE_CHOICE = "Будь ласка, зробіть свій вибір";
    public static final String LISTEN_TO_AUDIO = "Прослухайте аудіофайл";
    public static final String HELLO_MESSAGE = "Привіт, я дуже радий, що ти хочеш покращити свої знання англійської мови!!!" +
            " Маю надію, що я тобі в цьому допоможу!)) ";
    public static final String YOU_CHOOSE = "Ви вибрали ";
    public static final String INCORRECT_MESSAGE = "Не розумію, що ти маєш на увазі? ";
    public static final String DEVELOPMENT_OF_DIRECTION = " Триває розробка даного напрямку ";
    public static final String ENTER_ENGLISH_WORD = "Введіть слово англійською";
    public static final String ENTER_ENGLISH_UKRAINIAN_WORD = "Введіть слово англійською та його переклад через пробіл";
    public static final String WORD_ADDED = "Слово %s - %s було додано до словника.";
    public static final String WORD_EXISTS_IN_VOCABULARY = "Слово %s - %s вже існує у Вашому словнику.";
    public static final String WORD_DELETED = "Слово %s було видалено словника.";
    public static final String ERROR_ENTERING_WORD = "Помилка в написанні слова англійською, воно має бути написано тільки латиницею.";
    public static final String WORD_NOT_EXISTS_IN_VOCABULARY = "Слово %s відсутнє у Вашому словнику.";
    public static final String SELECT_RANGE = "У словнику більше 10 слів. Будь ласка виберіть діапазон слів для перегляду:";
    public static final String ADD_WORDS = "У Вас в словнику менше 10 слів. Для проходження уроку необхідно додати %s слів. Зараз у Вашому словнику знаходяться наступні слова:";
}
