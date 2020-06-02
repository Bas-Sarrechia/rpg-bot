package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmbedService {

    private final BasicCommandDao basicCommandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public EmbedBuilder generateExceptionEmbed(Exception e) {
        return new EmbedBuilder().setTitle("Error!").setDescription("Command \"" + e.getLocalizedMessage() + "\" not found.").setColor(Color.RED);
    }

    public EmbedBuilder generateBasicHelpEmbed(String commandText) throws CommandNotFoundException {

        EmbedBuilder helpEmbed = new EmbedBuilder();

        BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(commandText).orElseThrow(() -> new CommandNotFoundException("Command \"" + commandText + "\" not found."));

        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle(discordBotConfiguration.getPrefix() + commandText)
                .addField("USAGE", discordBotConfiguration.getPrefix() + commandText)
                .setDescription(basicCommand.getDescription() != null ? basicCommand.getDescription() : "Description has not been set yet.");

    }
}
