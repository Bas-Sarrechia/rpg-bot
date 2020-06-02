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

    public void setBasicCommandDescription(String command, String description) throws CommandNotFoundException {
        BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
        basicCommand.setDescription(description);
        basicCommandDao.save(basicCommand);
    }

    public BasicCommand lookupCommand(String command) throws CommandNotFoundException {
        return basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
    }

    public void removeCommand(String commandName) throws CommandNotFoundException {
        Command command = commandDao.findByCommandText(commandName).orElseThrow(() -> new CommandNotFoundException(commandName));
        commandDao.delete(command);
    }

    public void modifyCommand(String command, String respond) throws CommandNotFoundException {
        BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
        basicCommand.setResponse(respond);
        basicCommandDao.save(basicCommand);
    }

}
