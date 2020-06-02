package com.rpgbot.cs.discordbot.commandfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.event.message.MessageCreateEvent;

public interface ICommandHandler {

    void handle(MessageCreateEvent messageCreateEvent, String message);
}
