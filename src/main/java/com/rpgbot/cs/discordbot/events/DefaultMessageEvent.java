package com.rpgbot.cs.discordbot.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DefaultMessageEvent extends AbstractDiscordEvent {
    public DefaultMessageEvent(Object source, String message, long user, TextChannel target) {
        super(source, user, message, target);
    }
}
