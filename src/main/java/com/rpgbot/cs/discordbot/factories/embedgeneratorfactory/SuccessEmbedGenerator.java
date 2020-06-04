package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SuccessEmbedGenerator implements IEmbedGenerator {

	private final BasicCommandDao basicCommandDao;
	private final DiscordBotConfiguration discordBotConfiguration;

	public EmbedBuilder build(String input) {

		return new EmbedBuilder()
				.setColor(Color.GREEN)
				.setTitle("Success!")
				.setDescription(input);

	}
}
