package com.rpgbot.cs.discordbot;

import com.rpgbot.cs.discordbot.services.BotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscordBotApplication.class, args);
    }
}