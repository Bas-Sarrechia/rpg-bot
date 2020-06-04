package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserNotFoundExceptionEmbedGenerator implements IEmbedGenerator {

    private final BasicCommandDao basicCommandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public EmbedBuilder build(String message) {
        return new EmbedBuilder()
                .setTitle("Error!")
                .setDescription("User \"" + message + "\" not found.")
                .setColor(Color.RED);
    }
}
