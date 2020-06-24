package com.rpgbot.cs.discordbot.events;

import lombok.Data;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.reaction.ReactionEvent;

@Data
public class AbstractReactionEvent extends AbstractDiscordEvent {
    private final ReactionEvent origin;

    public AbstractReactionEvent(Object source, long user, String message, TextChannel target, ReactionEvent origin) {
        super(source, user, message, target);
        this.origin = origin;
    }
}
