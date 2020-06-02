package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotService {

    private final DiscordBotConfiguration discordBotConfiguration;
    @Getter
    private DiscordApi discordApi;

    @PostConstruct
    private void init() {
        // logs bot into discord
        this.discordApi = new DiscordApiBuilder().setToken(discordBotConfiguration.getToken()).login().join();
    }
}
