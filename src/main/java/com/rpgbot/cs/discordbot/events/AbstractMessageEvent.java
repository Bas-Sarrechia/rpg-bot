package com.rpgbot.cs.discordbot.events;

import lombok.Data;
import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;

@Data
public class AbstractMessageEvent extends AbstractDiscordEvent {

    private final MessageCreateEvent origin;

    public AbstractMessageEvent(Object source, long user, String message, TextChannel target, MessageCreateEvent origin) {
        super(source, user, message, target);
        this.origin = origin;
    }
}
