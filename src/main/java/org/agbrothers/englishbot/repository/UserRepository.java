package org.agbrothers.englishbot.repository;

import org.agbrothers.englishbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByChatId(String chatId);
}
