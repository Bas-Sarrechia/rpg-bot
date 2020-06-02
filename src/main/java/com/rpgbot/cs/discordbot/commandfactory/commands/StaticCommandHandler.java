package com.rpgbot.cs.discordbot.commandfactory.commands;

import com.rpgbot.cs.discordbot.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import org.aspectj.bridge.Message;
import org.javacord.api.event.message.MessageCreateEvent;

public class StaticCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    public StaticCommandHandler(CommandService commandService, EmbedService embedService, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedService, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            // looks up command in basicCommandDao
            BasicCommand basicCommand = super.getCommandService().lookupCommand(message.split(" ")[0]);
            messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
        } catch (CommandNotFoundException commandNotFoundException) {
            // Can be ignored, not every message starting with ~ is a command :)
        }
    }
}
