package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.daos.CommandDao;
import com.rpgbot.cs.discordbot.entities.*;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
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

    // adds command to basicCommandDao
    public void registerBasicCommand(String command, String respond) {
        // builds a BasicCommand, which cascades to Command using commandText
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

    // sets command description, throws CommandNotFoundException
    public void setCommandDescription(String commandName, String description) throws CommandNotFoundException {
        // finds command, throws exception if doesn't exist
        Command command = commandDao.findByCommandText(commandName).orElseThrow(() -> new CommandNotFoundException(commandName));
        // kinda self-explanatory, but sets the description
        command.setDescription(description);
        // persists the command
        commandDao.save(command);
    }

    // search for command in basicCommandDao
    public BasicCommand lookupCommand(String command) throws CommandNotFoundException {
        // looks up command in basicCommandDao, throws exception if not found
        return basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
    }

    // removes command from basicCommandDao
    public void removeCommand(String commandName) throws CommandNotFoundException {
        // searches for command in basicCommand
        Command command = commandDao.findByCommandText(commandName).orElseThrow(() -> new CommandNotFoundException(commandName));
        // big delete dun dun dun
        commandDao.delete(command);
    }

    // updates static/basic command in basicCommandDao
    public void modifyCommand(String command, String respond) throws CommandNotFoundException {
        // finds command from basicCommandDao, throws exception if can't find
        BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
        // sets new response
        basicCommand.setResponse(respond);
        // persists command
        basicCommandDao.save(basicCommand);
    }

}
