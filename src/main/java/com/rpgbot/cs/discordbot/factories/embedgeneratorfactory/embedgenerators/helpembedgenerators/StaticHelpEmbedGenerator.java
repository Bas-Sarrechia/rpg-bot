package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.CommandNotFoundExceptionEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaticHelpEmbedGenerator implements IEmbedGenerator {

    private final BasicCommandDao basicCommandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public EmbedBuilder build(String command) {
        try {
            BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
            return new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(discordBotConfiguration.getPrefix() + command)
                    .addField("USAGE", discordBotConfiguration.getPrefix() + command)
                    .setFooter(basicCommand.getCommand().getDescription() != null ? basicCommand.getCommand().getDescription() : "Description has not been set yet.");

        } catch (CommandNotFoundException commandNotFoundException) {
            return new CommandNotFoundExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration).build(commandNotFoundException.getMessage());
        }
    }

}
