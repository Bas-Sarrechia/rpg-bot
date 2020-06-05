package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators;

import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class CommandNotFoundExceptionEmbedGenerator implements IEmbedGenerator {
    @Override
    public EmbedBuilder build(String exceptionText) {
        // returns error embed
        return new EmbedBuilder().setTitle("Error!").setDescription("Command \"" + exceptionText + "\" not found.").setColor(Color.RED);
    }
}
