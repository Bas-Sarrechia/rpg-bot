package com.rpgbot.cs.discordbot.messagehandlers;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class DiscordMessage {

    @Getter
    private final Object body;
    @Getter
    @Setter
    private String[] emojis;

    private DiscordMessage() {
        this.body = null;
    }

    public DiscordMessage(String plain) {
        this.body = plain;
    }

    public DiscordMessage(EmbedBuilder embedded) {
        this.body = embedded;
    }

    public static DiscordMessage plain(String plain) {
        return new DiscordMessage(plain);
    }

    public static DiscordMessage embedded(EmbedBuilder embed) {
        return new DiscordMessage(embed);
    }

    public static DiscordMessage error(String description) {
        return new DiscordMessage(new EmbedBuilder().setColor(Color.red).setDescription(description));
    }

    public static DiscordMessage error(String description, String details) {
        return new DiscordMessage(new EmbedBuilder().setColor(Color.red).addField(description, details));
    }

    public static DiscordMessage error(EmbedBuilder embed) {
        return new DiscordMessage(embed.setColor(Color.red));
    }

}
