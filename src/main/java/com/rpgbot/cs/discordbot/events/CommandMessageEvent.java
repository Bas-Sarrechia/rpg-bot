package com.rpgbot.cs.discordbot.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;


@EqualsAndHashCode(callSuper = false)
public class CommandMessageEvent extends AbstractDiscordEvent {
    private final String command;
    @Getter
    private final String[] args;

    public CommandMessageEvent(Object source, String message, String command, String[] args, long user, TextChannel target, MessageCreateEvent origin) {
        super(source, user, message, target, origin);
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return this.command.toLowerCase();
    }
}
