package com.rpgbot.cs.discordbot.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DefaultMessageEvent extends AbstractDiscordEvent {
    public DefaultMessageEvent(Object source, String message, long user, TextChannel target, MessageCreateEvent origin) {
        super(source, user, message, target, origin);
    }
}
