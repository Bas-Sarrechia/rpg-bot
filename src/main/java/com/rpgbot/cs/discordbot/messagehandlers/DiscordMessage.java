package com.rpgbot.cs.discordbot.messagehandlers;

import com.rpgbot.cs.discordbot.entities.Dialog;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;


public class DiscordMessage {

    @Getter
    private Object body;
    @Setter
    @Getter
    private String[] emojis;
    @Getter
    @Setter
    private Dialog trackedDialog;

    private DiscordMessage() {
        this.body = null;
    }

    public DiscordMessage setTrackedDialog(Dialog dialog) {
        this.trackedDialog = dialog;
        return this;
    }

    public static DiscordMessage empty() {
        return new DiscordMessage();
    }

    public DiscordMessage setEmojis(String[] emojis) {
        this.emojis = emojis;
        return this;
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

    public static DiscordMessage success(String description) {
        return new DiscordMessage(new EmbedBuilder().setColor(Color.green).setDescription(description));
    }

    public static DiscordMessage success(String title, String description) {
        return new DiscordMessage(new EmbedBuilder().setColor(Color.green).setTitle(title).setDescription(description));
    }
}
