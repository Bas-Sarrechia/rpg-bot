package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.CommandNotFoundExceptionEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class StaticHelpEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {

    @Autowired
    public StaticHelpEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    public EmbedBuilder build(String command) {
        try {
            BasicCommand basicCommand = super.getBasicCommandDao().findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
            return new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(super.getDiscordBotConfiguration().getPrefix() + command)
                    .addField("USAGE", super.getDiscordBotConfiguration().getPrefix() + command)
                    .setFooter(basicCommand.getCommand().getDescription() != null ? basicCommand.getCommand().getDescription() : "Description has not been set yet.");

        } catch (CommandNotFoundException commandNotFoundException) {
            return new CommandNotFoundExceptionEmbedGenerator(super.getBasicCommandDao(), super.getDiscordBotConfiguration()).build(commandNotFoundException.getMessage());
        }
    }

}
