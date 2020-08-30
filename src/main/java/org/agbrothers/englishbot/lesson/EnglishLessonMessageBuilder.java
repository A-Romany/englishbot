package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.persistence.Word;
import org.agbrothers.englishbot.process.MessageBuilder;
import static org.agbrothers.englishbot.constant.MessageLabel.*;

public class EnglishLessonMessageBuilder extends MessageBuilder {

    private final Lesson lesson;


    public EnglishLessonMessageBuilder(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public String getResponseMessageText(String messageText) {
        String check="";
        if(lesson.getCurrentWord()!=null){
            if(lesson.getCurrentWord().getWordInUkrainian().equals(messageText)){
                lesson.setCountCorrectAnswers(lesson.getCountCorrectAnswers()+1);
                check=CORRECT_ANSWER;
            }
            else {
                lesson.setCountIncorrectAnswer(lesson.getCountIncorrectAnswer()+1);
                check=INCORRECT_ANSWER+lesson.getCurrentWord().getWordInUkrainian()+ NEWLINE;
            }
        }

        Word wordQuestion = lesson.getNextWord();
        if(wordQuestion==null) {
            return check+END_LESSON + lesson.getCountCorrectAnswers()+
                    FROM+ (lesson.getCountIncorrectAnswer()+ lesson.getCountCorrectAnswers())+ POINT + NEWLINE +
                    NEWLINE + RETURN_LESSON+ LinkLabel.ENGLISH+ NEWLINE + RETURN_MAIN_MENU+ NEXT_LESSON;
        }
        else {
            return check+wordQuestion.getEnglishValue();
        }

    }
}
