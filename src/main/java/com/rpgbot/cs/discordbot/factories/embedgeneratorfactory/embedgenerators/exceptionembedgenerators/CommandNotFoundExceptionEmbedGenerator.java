package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class CommandNotFoundExceptionEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {

    @Autowired
    public CommandNotFoundExceptionEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    @Override
    public EmbedBuilder build(String exceptionText) {
        // returns error embed
        return new EmbedBuilder().setTitle("Error!").setDescription("Command \"" + exceptionText + "\" not found.").setColor(Color.RED);
    }
}