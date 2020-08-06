package org.agbrothers.englishbot.lesson;

import org.agbrothers.englishbot.persistence.Word;
import org.agbrothers.englishbot.process.MessageBuilder;

public class EnglishLessonMessageBuilder extends MessageBuilder {

    private final Lesson lesson;


    public EnglishLessonMessageBuilder(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public String getResponseMessageText(String messageText) {//FIXME NullPointerException
        String check="";
        if(lesson.getCurrentWord()!=null){
            if(lesson.getCurrentWord().getWordInUkrainian().equals(messageText)){
                lesson.setCountCorrectAnswers(lesson.getCountCorrectAnswers()+1);
                check="Ви відповіли правильно! \n";
            }
            else {
                lesson.setCountIncorrectAnswer(lesson.getCountIncorrectAnswer()+1);
                check="Відповідь не вірна! Правильна відповідь - "+lesson.getCurrentWord().getWordInUkrainian()+"!!!\n";
            }
        }

        Word wordQuestion = lesson.getNextWord();
        if(wordQuestion==null) {
            return check+"Урок закінчено. Правильних відповідей - " + lesson.getCountCorrectAnswers()+
                    " із "+ (lesson.getCountIncorrectAnswer()+ lesson.getCountCorrectAnswers())+".\n\n"+
                    "\n Для повторного проходження уроку натисніть "+"/From_English_to_Ukrainian\n"
                    + RETURN_MAIN_MENU+ ",\nдля продовження навчання оберіть урок:";
        }
        else {
            return check+wordQuestion.getEnglishValue();
        }

    }
}
