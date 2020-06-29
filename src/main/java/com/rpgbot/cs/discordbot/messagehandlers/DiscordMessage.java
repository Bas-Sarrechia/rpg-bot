package com.rpgbot.cs.discordbot.messagehandlers;

import com.rpgbot.cs.discordbot.entities.Dialog;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.Optional;
public class DiscordMessage<T> {

    @Getter
    private final Optional<T> body;
    @Getter @Setter
    private String[] emojis;
    @Setter @Getter
    private Dialog trackedDialog;

    private DiscordMessage() {
        this.body = Optional.empty();
    }

    private DiscordMessage(T body) {
        this.body = Optional.of(body);
    }

    public static DiscordMessage<Void> empty() {
        return new DiscordMessage<>();
    }

    public static DiscordMessage<String> plain(String plain) {
        return new DiscordMessage<>(plain);
    }

    public static DiscordMessage<EmbedBuilder> embedded(EmbedBuilder embed) {
        return new DiscordMessage<>(embed);
    }

    public static DiscordMessage<EmbedBuilder> error(String description) {
        return new DiscordMessage<>(new EmbedBuilder().setColor(Color.red).setDescription(description));
    }

    public static DiscordMessage<EmbedBuilder> success(String description) {
        return new DiscordMessage<>(new EmbedBuilder().setColor(Color.green).setDescription(description));
    }

    public static DiscordMessage<EmbedBuilder> success(String title, String description) {
        return new DiscordMessage<>(new EmbedBuilder().setColor(Color.green).setTitle(title).setDescription(description));
    }
}
