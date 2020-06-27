package com.rpgbot.cs.discordbot.events;

import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;

@Getter
public class AbstractMessageEvent extends AbstractDiscordEvent {

    private final MessageCreateEvent origin;
    private final String message;

    public AbstractMessageEvent(Object source, long user, String message, TextChannel target, MessageCreateEvent origin) {
        super(source, user, target);
        this.message = message;
        this.origin = origin;
    }
}
