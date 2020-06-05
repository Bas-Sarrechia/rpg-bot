package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.beryx.awt.color.ColorFactory;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SetEmbedColorCommandHandler implements ICommandHandler {

	private final CommandService commandService;
	private final EmbedGeneratorFactory embedGeneratorFactory;
	private final DiscordBotConfiguration discordBotConfiguration;

	@Override
	public void handle(MessageCreateEvent messageCreateEvent, String message) {
		// checks for enough args
		if (message.split(" ").length > 2) {
			// gets command
			String command = message.split(" ")[1];
			// gets color
			Color color = ColorFactory.web(message.split(" ")[2]);
			try {
				// sets embed color
				commandService.setEmbedColorCommand(command, color);
				// success embed
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build(command + " color set to " + message.split(" ")[2]));
			} catch (CommandNotFoundException commandNotFoundException) {
				// error embed
				messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNOTFOUNDEXCEPTION).build(commandNotFoundException.getMessage()));
			}
		} else {
			// explains how to use command
			messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getSetEmbedColorCommand()).build("uh oh i broke! contact mods plz"));
		}
	}
}
