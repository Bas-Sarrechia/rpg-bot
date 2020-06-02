package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
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

    // generates exception embed
    public EmbedBuilder generateExceptionEmbed(Exception e, ExceptionType type) {
        // instantiates string
        String message;
        // sets message depending on enum value
        switch (type) {
            case COMMANDNOTFOUND:
                message = "Command \"" + e.getLocalizedMessage() + "\" not found.";
                break;

            case USERNOTFOUND:
                message = "User not found. Contact a moderator";
                break;

            default:
                message = e.getMessage();
                break;
        }
        // returns error embed
        return new EmbedBuilder().setTitle("Error!").setDescription(message).setColor(Color.RED);
    }

    // generates help embed for static/basic commands, throws CommandNotFoundException
    public EmbedBuilder generateBasicHelpEmbed(String commandText) throws CommandNotFoundException {
        //instantiates embed
        EmbedBuilder helpEmbed = new EmbedBuilder();
        // searches for command, throws exception if not found
        BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(commandText).orElseThrow(() -> new CommandNotFoundException(commandText));
        //constructs and returns embed
        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle(discordBotConfiguration.getPrefix() + commandText)
                .addField("USAGE", discordBotConfiguration.getPrefix() + commandText)
                .setDescription(basicCommand.getCommand().getDescription() != null ? basicCommand.getCommand().getDescription() : "Description has not been set yet.");

    }
}
