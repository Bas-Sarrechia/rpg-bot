package com.rpgbot.cs.discordbot.commandfactory.commands;

import com.rpgbot.cs.discordbot.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class RemoveCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    public RemoveCommandHandler(CommandService commandService, EmbedService embedService, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedService, discordBotConfiguration);
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
                messageCreateEvent.getChannel().sendMessage(super.getEmbedService().generateExceptionEmbed(commandNotFoundException, ExceptionType.COMMANDNOTFOUND));
            }
        }
    }
}
