package org.agbrothers.englishbot.repository;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    Word findByEnglishValue(String englishValue);

    @Query("SELECT id from Word")
    List<Long> getAllIdWord();

    @Query("from Word w where w.id in :ids")
    List<Word> findWordsForLesson(@Param("ids") Iterable<Long> ids);
}
