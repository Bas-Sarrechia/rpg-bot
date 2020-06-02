package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.daos.CommandDao;
import com.rpgbot.cs.discordbot.entities.*;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Basic;
import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final BotService botService;
    private final BasicCommandDao basicCommandDao;
    private final CommandDao commandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public void registerBasicCommand(String command, String respond) {
        BasicCommand basicCommand = basicCommandDao.save(BasicCommand.builder()
                .response(respond)
                .command(Command.builder()
                        .commandText(command)
                        .requiredAuthorization(Authorization.BASIC)
                        .commandType(CommandType.BASIC)
                        .build())
                .build());

        basicCommandDao.save(basicCommand);

    }

    public boolean setBasicCommandDescription(String command, String description) {
        Optional<BasicCommand> basicCommandOptional = basicCommandDao.findByCommandCommandText(command);
        if (basicCommandOptional.isPresent()) {
            BasicCommand basicCommand = basicCommandOptional.get();
            basicCommand.setDescription(description);
            basicCommandDao.save(basicCommand);
            return true;
        }
        return false;
    }

    public boolean setBasicCommandUsage(String command, String usage) {
        Optional<BasicCommand> basicCommandOptional = basicCommandDao.findByCommandCommandText(command);
        if (basicCommandOptional.isPresent()) {
            BasicCommand basicCommand = basicCommandOptional.get();
            basicCommand.setUsage(usage);
            basicCommandDao.save(basicCommand);
            return true;
        }
        return false;
    }

    public BasicCommand lookupCommand(String command) {
        return basicCommandDao.findByCommandCommandText(command).orElse(null);
    }

    public boolean removeCommand(String commandName) {
        Optional<Command> command = commandDao.findByCommandText(commandName);
        if (command.isPresent()) {
            commandDao.delete(command.get());
            return true;
        }
        return false;
    }

    public boolean modifyCommand(String command, String respond) {
        Optional<BasicCommand> basicCommandOptional = basicCommandDao.findByCommandCommandText(command);
        if (basicCommandOptional.isPresent()) {
            BasicCommand basicCommand = basicCommandOptional.get();
            basicCommand.setResponse(respond);
            basicCommandDao.save(basicCommand);
            return true;
        }
        return false;
    }

    public EmbedBuilder generateHelpEmbed(String commandText) {

        EmbedBuilder helpEmbed = new EmbedBuilder();

        Optional<BasicCommand> basicCommandOptional = basicCommandDao.findByCommandCommandText(commandText);
        if (basicCommandOptional.isPresent()) {
            helpEmbed.setColor(Color.GREEN);
            helpEmbed.setTitle(discordBotConfiguration.getPrefix() + commandText);
            BasicCommand basicCommand = basicCommandOptional.get();
            if (basicCommand.getDescription() != null) {
                helpEmbed.setDescription(basicCommand.getDescription());
            } else {
                helpEmbed.setDescription("Description has not yet been set.");
            }
            if (basicCommand.getUsage() != null) {
                helpEmbed.addField("USAGE", basicCommand.getUsage());
            } else {
                helpEmbed.addField("USAGE", "Usage has not been defined yet.");
            }
        } else {
            helpEmbed.addField("Error!", "Command \"" + commandText + "\" not found.");
            helpEmbed.setColor(Color.RED);
        }
        return helpEmbed;
    }

}
