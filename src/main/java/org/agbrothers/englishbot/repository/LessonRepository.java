package org.agbrothers.englishbot.repository;

import org.agbrothers.englishbot.entity.Lesson;
import org.agbrothers.englishbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findLessonByUser(User user);
}
