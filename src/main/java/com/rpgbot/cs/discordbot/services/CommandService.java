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
import com.rpgbot.cs.discordbot.exceptions.CommandNameTakenException;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final BasicCommandDao basicCommandDao;
    private final CommandDao commandDao;
    private final EmbedCommandDao embedCommandDao;


    // adds command to embedCommandDao
    public void registerEmbedCommand(String command, String description)  throws CommandNameTakenException {
        try {
        	Command commandObject = commandDao.findByCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
        	throw new CommandNameTakenException(command);
        } catch (CommandNotFoundException commandNotFoundException) {
        	// can ignore exception, used to confirm that the command doesn't already exist
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
    }

    // sets embed description, throws CommandNotFoundException
	public void setEmbedDescriptionCommand(String command, String description) throws CommandNotFoundException {
    	// finds command, throws exception if not found
    	EmbedCommand embedCommand = embedCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
		// sets embed description
		embedCommand.setDescription(description);
		// persists
		embedCommandDao.save(embedCommand);
    }

    // searches for EmbedCommand
	public EmbedCommand lookupEmbedCommand(String command) throws CommandNotFoundException {
    	return embedCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
	}

	// sets color of EmbedCommand
	public void setEmbedColorCommand(String command, Color color) throws CommandNotFoundException {
    	EmbedCommand embedCommand = embedCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
    	embedCommand.setColor(color);
    	embedCommandDao.save(embedCommand);
	}

	// sets title of EmbedCommand
	public void setEmbedTitleCommand(String command, String title) throws CommandNotFoundException {
    	EmbedCommand embedCommand = embedCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
    	embedCommand.setTitle(title);
    	embedCommandDao.save(embedCommand);
	}

	// adds command to basicCommandDao
	public void registerBasicCommand(String command, String respond) throws CommandNameTakenException {
    	Optional<Command> commandOptional = commandDao.findByCommandText(command);
    	if (commandOptional.isPresent()) {
    		throw new CommandNameTakenException(command);
	    }
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

	// renames command
	public void renameCommand(String oldName, String newName) throws CommandNameTakenException, CommandNotFoundException {
    	Optional<Command> newCommand = commandDao.findByCommandText(newName);
    	if (newCommand.isPresent()) {
    		throw new CommandNameTakenException(newName);
	    }
    	Command command = commandDao.findByCommandText(oldName).orElseThrow(() -> new CommandNotFoundException(oldName));
    	command.setCommandText(newName);
    	commandDao.save(command);
	}

    // sets command description, throws CommandNotFoundException
    public void setStaticDescriptionCommand(String commandName, String description) throws CommandNotFoundException {
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

    // removes command from basicCommandDao
    public void removeStaticCommand(String commandName) throws CommandNotFoundException {
        // searches for command in basicCommand
        Command command = commandDao.findByCommandText(commandName).orElseThrow(() -> new CommandNotFoundException(commandName));
        // big delete dun dun dun
        commandDao.delete(command);
    }

    // updates static/basic command in basicCommandDao
    public void modifyStaticCommand(String command, String respond) throws CommandNotFoundException {
        // finds command from basicCommandDao, throws exception if can't find
        BasicCommand basicCommand = basicCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
        // sets new response
        basicCommand.setResponse(respond);
        // persists command
        basicCommandDao.save(basicCommand);
    }

}
