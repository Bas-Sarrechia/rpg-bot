package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.entities.Authorization;
import com.rpgbot.cs.discordbot.entities.User;
import com.rpgbot.cs.discordbot.exception.UserExistsException;
import com.rpgbot.cs.discordbot.exception.UserNotExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final DiscordUserDao discordUserDao;

    public UserService(DiscordUserDao discordUserDao) {
        this.discordUserDao = discordUserDao;
    }


    public void register(Long discordId) {
        discordUserDao.findById(discordId).ifPresentOrElse(user -> {
            throw new UserExistsException();
        }, () -> {
            User user = new User();
            user.setId(discordId);
            user.setAuthorization(Authorization.BASIC);
            this.discordUserDao.save(user);
        });

    }

    public User findUserById(long userId) {
        return discordUserDao.findById(userId).orElseThrow(UserNotExistsException::new);
    }
}
