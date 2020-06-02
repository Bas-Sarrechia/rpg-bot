package com.rpgbot.cs.discordbot.factories.commandfactory;

import org.javacord.api.event.message.MessageCreateEvent;

public interface ICommandHandler {
    void handle(MessageCreateEvent messageCreateEvent, String message);
}
