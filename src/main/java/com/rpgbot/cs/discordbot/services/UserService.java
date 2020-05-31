package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final DiscordUserDao discordUserDao;

    public UserService(DiscordUserDao discordUserDao) {
        this.discordUserDao = discordUserDao;
    }


    public void register(DiscordUser discordUser) {
        this.discordUserDao.save(discordUser);
    }

    public DiscordUser findUserById(long userId) {
        return discordUserDao.findById(userId).orElse(null);
    }
}
