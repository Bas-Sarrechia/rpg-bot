package com.rpgbot.cs.discordbot.events;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.reaction.ReactionEvent;

public class SelfEmbedReactionEvent extends AbstractReactionEvent {
    public SelfEmbedReactionEvent(Object source, long user, String message, TextChannel target, ReactionEvent origin) {
        super(source, user, message, target, origin);
    }
}
