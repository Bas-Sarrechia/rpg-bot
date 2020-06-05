package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandNameTakenExceptionEmbedGenerator implements IEmbedGenerator {

	private final DiscordBotConfiguration discordBotConfiguration;

	@Override
	public EmbedBuilder build(String input) {
		return new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle("Error!")
				.setDescription("There is already a command with the name " + input)
				.setFooter("you can either pick a new name for this command or you can use " + discordBotConfiguration.getPrefix() + discordBotConfiguration.getRenameCommand());
	}
}
