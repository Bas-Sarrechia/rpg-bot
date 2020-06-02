package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public interface IEmbedGenerator {
    EmbedBuilder build(String input);
}
