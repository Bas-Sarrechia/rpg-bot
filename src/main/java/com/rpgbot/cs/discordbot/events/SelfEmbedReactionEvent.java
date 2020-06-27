package com.rpgbot.cs.discordbot.events;

import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionEvent;
@Getter
public class SelfEmbedReactionEvent extends AbstractReactionEvent {
    public SelfEmbedReactionEvent(Object source, long user, String message, TextChannel target, ReactionAddEvent origin) {
        super(source, user, message, target, origin);
    }
}
