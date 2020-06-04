package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators;

import com.rpgbot.cs.discordbot.daos.EmbedCommandDao;
import com.rpgbot.cs.discordbot.entities.EmbedCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.CommandNotFoundExceptionEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandEmbedGenerator implements IEmbedGenerator {

	private final EmbedCommandDao embedCommandDao;

	@Override
	public EmbedBuilder build(String command) {
		try {
			EmbedCommand embedCommand = embedCommandDao.findByCommandCommandText(command).orElseThrow(() -> new CommandNotFoundException(command));
			return new EmbedBuilder()
					.setTitle(embedCommand.getTitle())
					.setDescription(embedCommand.getDescription())
					.setColor(embedCommand.getColor() != null ? embedCommand.getColor() : Color.PINK);
		} catch (CommandNotFoundException commandNotFoundException) {
			return new CommandNotFoundExceptionEmbedGenerator().build(commandNotFoundException.getMessage());
		}
	}
}
