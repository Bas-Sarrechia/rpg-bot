package com.rpgbot.cs.discordbot.events;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionEvent;

@Getter
public class AbstractReactionEvent extends AbstractDiscordEvent {
    private final ReactionEvent origin;
    private final String emoji;

    public AbstractReactionEvent(Object source, long user, String emoji, TextChannel target, ReactionAddEvent origin) {
        super(source, user, target);
        this.origin = origin;
        this.emoji = emoji;
    }
}
