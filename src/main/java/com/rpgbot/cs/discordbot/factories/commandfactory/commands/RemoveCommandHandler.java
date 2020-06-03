package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class RemoveCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    @Autowired
    public RemoveCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        // checks that there's at least one argument
        if (message.indexOf(" ") > -1) {
            try {
                // removes command from basicCommandDao, throws CommandNotFoundException
                super.getCommandService().removeCommand(message.split(" ")[1]);
                // success!
                messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Success!")
                        .setDescription("Command removed: " + message.split(" ")[1])
                );
            } catch (CommandNotFoundException commandNotFoundException) {
                // command not found error
                messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().get(EmbedType.COMMANDNOTFOUNDEXCEPTION).build(commandNotFoundException.getMessage()));
            }
        }
    }
}
