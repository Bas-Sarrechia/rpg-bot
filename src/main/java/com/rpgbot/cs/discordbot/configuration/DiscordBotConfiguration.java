package com.rpgbot.cs.discordbot.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("discordbot")
public class DiscordBotConfiguration {
    private String token;
    private String prefix;

    // commands
    private String createCommand;
    private String helpCommand;
    private String modifyCommand;
    private String profileCommand;
    private String registerCommand;
    private String removeCommand;
    private String setColorCommand;
}
