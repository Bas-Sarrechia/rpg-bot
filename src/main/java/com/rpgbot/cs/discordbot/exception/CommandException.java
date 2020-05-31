package com.rpgbot.cs.discordbot.exception;

import org.javacord.api.entity.channel.TextChannel;

public class CommandException extends AbstractBotException {

    public CommandException(TextChannel channel, String commandUsage, String details) {
        super(channel, details, commandUsage);
    }
}
