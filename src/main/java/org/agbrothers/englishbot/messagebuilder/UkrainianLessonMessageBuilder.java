package org.agbrothers.englishbot.messagebuilder;

import org.agbrothers.englishbot.constant.LinkLabel;
import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.Word;

import static org.agbrothers.englishbot.constant.MessageLabel.*;

public class UkrainianLessonMessageBuilder extends MessageBuilder{
    private final Lesson lesson;

    public UkrainianLessonMessageBuilder(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public String getResponseMessageText(String messageText) {
        String check="";
        if(lesson.getCurrentWord()!=null){
            if(lesson.getCurrentWord().getEnglishValue().equals(messageText)){
                lesson.setCountCorrectAnswers(lesson.getCountCorrectAnswers()+1);
                check=CORRECT_ANSWER;
            }
            else {
                lesson.setCountIncorrectAnswer(lesson.getCountIncorrectAnswer()+1);
                check=INCORRECT_ANSWER+lesson.getCurrentWord().getEnglishValue()+ NEWLINE;
            }
        }

        Word wordQuestion = lesson.getNextWord();
        if(wordQuestion==null) {
            return check+END_LESSON + lesson.getCountCorrectAnswers()+
                    " із "+ (lesson.getCountIncorrectAnswer()+ lesson.getCountCorrectAnswers())+ POINT + NEWLINE +
                    NEWLINE + RETURN_LESSON+ LinkLabel.UKRAINIAN+ NEWLINE + RETURN_MAIN_MENU+ NEXT_LESSON;
        }
        else {
            return check+wordQuestion.getUkrainianValue();
        }
    }
}
