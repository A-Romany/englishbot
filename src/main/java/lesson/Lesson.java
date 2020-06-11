package lesson;

import constant.Language;
import persistence.Word;
import service.WordPoolService;

import java.util.*;

public class Lesson {

    private final Language language;
    private final Set<Word> wordPool;//word in Lesson
    private final Set<Word> answersPool;
    private WordPoolService wordPoolService;

    public Lesson(WordPoolService wordPoolService, Language language) {
        this.wordPoolService = wordPoolService;
        this.wordPool = wordPoolService.getRandomWordPool(); //TODO no service at entity class should be present
        this.answersPool = new HashSet<>(wordPool);
        this.language = language;
    }

    public Word getNextWord(){
        Iterator<Word> iterator = wordPool.iterator();
        if(iterator.hasNext()){
            Word word = iterator.next();
            iterator.remove();
            return word;
        }

        return null;
    };

    public Set<Word> getTranslationOptions(Word correctAnswer, Set<Word> answersPool) {
        Set<Word> getAnswerOptions = new HashSet<>();

        for (int i = 0; i < 5; i++) {

        }

        List<Word> words = new ArrayList<>(answersPool).subList(0,4); // можна відмовитись від List на користь Set
        words.add(correctAnswer);

        Collections.shuffle(words);

        return new HashSet<>(words);
    };

}
