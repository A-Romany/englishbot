package constant;

public enum ButtonLabel {

    ADD_WORD("Додати нове слово"),
    DICTIONARY("Dictionary"),
    EDIT_DICTIONARY("Відредактувати дані"),
    ENGLISH("From English to Ukrainian"),
    LESSONS("Lessons"),
    PRINT_ALL_WORD("Друк всіх слів"),
    REMOVE_WORD("Видалити слово"),
    UKRAINIAN("From Ukrainian to English"),
    MAIN_MENU("/markup");


    public String label;

    ButtonLabel(String label) {
        this.label = label;
    }
}
