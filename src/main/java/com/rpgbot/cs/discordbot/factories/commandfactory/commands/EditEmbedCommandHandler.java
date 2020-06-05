package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.EmbedCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EditEmbedCommandHandler implements ICommandHandler {

	private final CommandService commandService;
	private final EmbedGeneratorFactory embedGeneratorFactory;
	private final DiscordBotConfiguration discordBotConfiguration;

	@Override
	public void handle(MessageCreateEvent messageCreateEvent, String message) {
		// checks for enough args
		if (message.split(" ").length > 2) {
			// gets embed command
			String command = message.split(" ")[1];
			// gets embed description
			String description = String.join( " ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
			try {
				// thank heaven for CommandService
				commandService.setEmbedDescriptionCommand(command, description);
				// success embed
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build("Embed description edited: " + command));
			} catch (CommandNotFoundException commandNotFoundException) {
				// exception embed :D
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNOTFOUNDEXCEPTION).build(commandNotFoundException.getMessage()));
			}
		} else {
			// explains how to properly use command
			messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getSetEmbedDescriptionCommand()).build("if you're seeing this, ping an admin plzzz :O"));
		}
	}
}
