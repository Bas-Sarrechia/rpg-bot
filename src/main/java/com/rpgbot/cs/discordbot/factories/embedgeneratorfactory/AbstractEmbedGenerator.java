package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

@Getter
@RequiredArgsConstructor
public abstract class AbstractEmbedGenerator {

    private final BasicCommandDao basicCommandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public EmbedBuilder build(String input) {
        return new EmbedBuilder()
                           .setColor(Color.RED)
                           .setTitle("Error!")
                           .setDescription("EmbedFactory Failed...");
    }

}
