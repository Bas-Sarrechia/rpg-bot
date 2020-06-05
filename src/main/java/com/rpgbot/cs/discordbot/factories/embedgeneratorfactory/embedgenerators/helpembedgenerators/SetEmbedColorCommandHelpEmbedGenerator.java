package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SetEmbedColorCommandHelpEmbedGenerator implements IEmbedGenerator {

	private final DiscordBotConfiguration discordBotConfiguration;

	@Override
	public EmbedBuilder build(String input) {
		return new EmbedBuilder()
				.setColor(Color.RED)
				.setTitle(discordBotConfiguration.getPrefix() + discordBotConfiguration.getSetEmbedColorCommand())
				.addField("USAGE", discordBotConfiguration.getPrefix() + discordBotConfiguration.getSetEmbedColorCommand() + " <command> <color>")
				.setFooter("sets the color of an embed");
	}
}
