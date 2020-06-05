package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNameTakenException;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RenameCommandCommandHandler implements ICommandHandler {

	private final CommandService commandService;
	private final EmbedGeneratorFactory embedGeneratorFactory;
	private final DiscordBotConfiguration discordBotConfiguration;

	@Override
	public void handle(MessageCreateEvent messageCreateEvent, String message) {
		// checks for enough args
		if (message.split(" ").length == 3) {
			// gets old name
			String oldName = message.split(" ")[1];
			// gets new name
			String newName = message.split(" ")[2];
			try {
				// command service saving the day again
				commandService.renameCommand(oldName, newName);
				// success embed
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build(oldName + " renamed to: " + newName));
			} catch (CommandNotFoundException commandNotFoundException) {
				// old command name doesn't exist error
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNOTFOUNDEXCEPTION).build(commandNotFoundException.getMessage()));
			} catch (CommandNameTakenException commandNameTakenException) {
				// new command name already exists
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNAMETAKENEXCEPTION).build(commandNameTakenException.getMessage()));
			}
		} else {
			messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getRenameCommand()).build("if you're seeing this pls contact mod"));
		}
	}
}
