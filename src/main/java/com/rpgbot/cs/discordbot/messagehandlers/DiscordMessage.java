package com.rpgbot.cs.discordbot.messagehandlers;

import com.rpgbot.cs.discordbot.entities.Dialog;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;


public class DiscordMessage {

    @Getter
    private Object body;
    @Setter @Getter
    private String[] emojis;
    @Getter @Setter
    private Dialog trackedDialog;

    private DiscordMessage() {
        this.body = null;
    }

    public static class DiscordMessageBuilder {
        private final DiscordMessage discordMessage;

        public DiscordMessageBuilder() {
            this.discordMessage = new DiscordMessage();
        }

        public DiscordMessageBuilder setEmojis(String[] emojis) {
            discordMessage.setEmojis(emojis);
            return this;
        }

        public DiscordMessageBuilder setBody(String body) {
            this.discordMessage.body = body;
            return this;
        }

        public DiscordMessageBuilder setBody(EmbedBuilder body) {
            this.discordMessage.body = body;
            return this;
        }

        public DiscordMessageBuilder setTrackedDialog(Dialog dialog) {
            this.discordMessage.trackedDialog = dialog;
            return this;
        }

        public DiscordMessage build() {
            return this.discordMessage;
        }
    }

    public DiscordMessage setTrackedDialog(Dialog dialog) {
        this.trackedDialog = dialog;
        return this;
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

}
