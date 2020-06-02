package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class HelpEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {

    public HelpEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    @Override
    public EmbedBuilder build(String command) {
        try {
            BasicCommand basicCommand = super.getBasicCommandDao().findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
            return new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle(super.getDiscordBotConfiguration().getPrefix() + command)
                    .addField("USAGE", super.getDiscordBotConfiguration().getPrefix() + command)
                    .setDescription(basicCommand.getCommand().getDescription() != null ? basicCommand.getCommand().getDescription() : "Description has not been set yet.");

        } catch (CommandNotFoundException commandNotFoundException) {
            return new CommandNotFoundExceptionEmbedGenerator(super.getBasicCommandDao(), super.getDiscordBotConfiguration()).build(commandNotFoundException.getMessage());
        }
    }

}
