package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class UserNotFoundExceptionEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {
    public UserNotFoundExceptionEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    @Override
    public EmbedBuilder build(String message) {
        return new EmbedBuilder()
                .setTitle("Error!")
                .setDescription("User \"" + message + "\" not found.")
                .setColor(Color.RED);
    }
}
