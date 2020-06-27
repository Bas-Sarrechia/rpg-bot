package com.rpgbot.cs.discordbot.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;


@EqualsAndHashCode(callSuper = false)
@Getter
public class CommandMessageEvent extends AbstractMessageEvent {
    private final String command;
    private final String[] args;

    public CommandMessageEvent(Object source, String message, String command, String[] args, long user, TextChannel target, MessageCreateEvent origin) {
        super(source, user, message, target, origin);
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return this.command.toLowerCase();
    }

    public void sendError(String details, String help) {
        if (details.isEmpty()) {
            sendError(help);
        }
        if (help.isEmpty()) {
            throw new IllegalArgumentException("help cannot be empty");
        }
        super.getTarget().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .addField("How to use this command", help)
                .setFooter(details));
    }

    public void sendError(String help) {
        if (help.isEmpty()) {
            throw new IllegalArgumentException("help cannot be empty");
        }
        super.getTarget().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .addField("How to use this command", help));
    }
}
