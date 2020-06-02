package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.factories.commandfactory.CommandHandlerFactory;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.services.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandRouter {
    private final BotService botService;
    private final DiscordBotConfiguration discordBotConfiguration;
    private final CommandHandlerFactory commandHandlerFactory;

    // handles any message
    @PostConstruct
    public void handleMessage() {
        // creates message listener
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
           String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
           if (validateInput(message)) {
                commandHandlerFactory.get(getValidatedString(message).split(" ")[0]).handle(messageCreateEvent, getValidatedString(message));
           }
        });
    }

    // gets message without prefix
    private String getValidatedString(String input) {
        // if length of the first word in message is greater than prefix length
        if (input.split(" ")[0].length() > discordBotConfiguration.getPrefix().length()) {
            // return substring starting from prefix length
            return input.substring(discordBotConfiguration.getPrefix().length());
        }
        // else, return prefix
        return discordBotConfiguration.getPrefix();
    }

    // checks that a message begins with prefix
    private boolean validateInput(String input) {
        return input.startsWith(discordBotConfiguration.getPrefix());
    }

 }
