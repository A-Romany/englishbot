package org.agbrothers.englishbot.repository;

import org.agbrothers.englishbot.entity.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends CrudRepository <Word, Long>{


}
