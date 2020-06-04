package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.daos.CommandDao;
import com.rpgbot.cs.discordbot.daos.EmbedCommandDao;
import com.rpgbot.cs.discordbot.entities.Authorization;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.entities.Command;
import com.rpgbot.cs.discordbot.entities.CommandType;
import com.rpgbot.cs.discordbot.entities.EmbedCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final BasicCommandDao basicCommandDao;
    private final CommandDao commandDao;
    private final EmbedCommandDao embedCommandDao;

    // adds command to basicCommandDao
    public void registerBasicCommand(String command, String respond) {
        // builds a BasicCommand
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
    // adds command to embedCommandDao
    public void registerEmbedCommand(String command, String description) {
        // builds an EmbedCommand
        EmbedCommand embedCommand = embedCommandDao.save(EmbedCommand.builder()
                .title(command)
                .description(description)
                .command(Command.builder()
                        .commandText(command)
                        .requiredAuthorization(Authorization.BASIC)
                        .commandType(CommandType.EMBED)
                        .build())
                .build());
        embedCommandDao.save(embedCommand);
    }

    // sets command description, throws CommandNotFoundException
    public void setDescriptionStaticCommand(String commandName, String description) throws CommandNotFoundException {
        // finds command, throws exception if doesn't exist
        Command command = commandDao.findByCommandText(commandName).orElseThrow(() -> new CommandNotFoundException(commandName));
        // kinda self-explanatory, but sets the description
        command.setDescription(description);
        // persists the command
        commandDao.save(command);
    }

    // search for command in basicCommandDao
    public BasicCommand lookupStaticCommand(String command) throws CommandNotFoundException {
        // looks up command in basicCommandDao, throws exception if not found
        return basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
    }

    // search for command in embedCommandDao
    public EmbedCommand lookupEmbedCommand(String command) throws CommandNotFoundException {
        return embedCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
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
