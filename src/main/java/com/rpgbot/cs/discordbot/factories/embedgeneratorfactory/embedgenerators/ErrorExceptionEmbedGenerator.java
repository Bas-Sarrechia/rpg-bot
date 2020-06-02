package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class ErrorExceptionEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {
    public ErrorExceptionEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    @Override
    public EmbedBuilder build(String input) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Error!")
                .setDescription("EmbedGenerator encountered an error...");
    }
}
