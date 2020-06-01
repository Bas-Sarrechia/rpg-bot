package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BotService {

    private final DiscordBotConfiguration discordBotConfiguration;
    @Getter
    private DiscordApi discordApi;

    @Autowired
    public BotService(DiscordBotConfiguration discordBotConfiguration) {
        this.discordBotConfiguration = discordBotConfiguration;
    }

    @PostConstruct
    private void init() {
        this.discordApi = new DiscordApiBuilder().setToken(discordBotConfiguration.getToken()).login().join();
    }
}
