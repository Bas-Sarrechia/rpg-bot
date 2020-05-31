package com.rpgbot.cs.discordbot.exception;

import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;

@Getter
public abstract class AbstractBotException extends RuntimeException {

    private final TextChannel channel;
    private final String details;
    private final String body;

    protected AbstractBotException(TextChannel channel, String details, String body) {
        super("command was badly formatted");
        this.channel = channel;
        this.details = details;
        this.body = body;
    }
}
