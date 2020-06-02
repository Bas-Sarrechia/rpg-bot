package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class CharactersCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    public CharactersCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.PINK)
                .setAuthor("Hi"));
    }
}
