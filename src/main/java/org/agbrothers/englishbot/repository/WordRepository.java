package org.agbrothers.englishbot.repository;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    Word findByEnglishValue(String englishValue);

    @Query("SELECT id from Word")
    List<Long> getWordIds();

    List<Word> findWordsByIdIn(List<Long> id);
}
