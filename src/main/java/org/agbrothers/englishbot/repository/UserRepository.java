package org.agbrothers.englishbot.repository;

import org.agbrothers.englishbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT chatId from User")
    List<String> getChatIdsUser();

    @Query("SELECT user FROM User user WHERE user.chatId = :chatId")
    User getUserByChatId(@Param("chatId") String chatId);
}
