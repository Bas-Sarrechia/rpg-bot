package com.rpgbot.cs.discordbot.events;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class AbstractDiscordEvent extends ApplicationEvent {
    private final long user;
    private final String message;
    private final TextChannel target;
    private final MessageCreateEvent origin;
    @Setter
    private boolean hasPropagated;

    public AbstractDiscordEvent(Object source, long user, String message, TextChannel target, MessageCreateEvent origin) {
        super(source);
        this.user = user;
        this.message = message;
        this.target = target;
        this.origin = origin;
    }
    public TextChannel getTarget() {
        this.hasPropagated = true;
        return this.target;
    }
}
