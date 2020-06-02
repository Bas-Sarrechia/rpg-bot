package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

import static com.rpgbot.cs.discordbot.exceptions.ExceptionType.COMMANDNOTFOUND;
import static com.rpgbot.cs.discordbot.exceptions.ExceptionType.USERNOTFOUND;

public class CommandNotFoundExceptionEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {
    public CommandNotFoundExceptionEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    @Override
    public EmbedBuilder build(String exceptionText) {
        // returns error embed
        return new EmbedBuilder().setTitle("Error!").setDescription("Command \"" + exceptionText + "\" not found.").setColor(Color.RED);
    }
}
