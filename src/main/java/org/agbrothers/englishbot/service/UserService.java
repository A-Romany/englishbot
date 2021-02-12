package org.agbrothers.englishbot.service;

import org.agbrothers.englishbot.entity.User;
import org.agbrothers.englishbot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveAndFlushUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public User getUserByChatId(String chatId) {
        return userRepository.findUserByChatId(chatId);
    }
}
