package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.entities.EmbedCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.CommandEmbedGenerator;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmbedCommandHandler implements ICommandHandler {

    private final CommandService commandService;
    private final CommandEmbedGenerator commandEmbedGenerator;

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            // looks up command in basicCommandDao
            EmbedCommand embedCommand = commandService.lookupEmbedCommand(message.split(" ")[0]);
            messageCreateEvent.getChannel().sendMessage(commandEmbedGenerator.build(embedCommand.getCommand().getCommandText()));
        } catch (CommandNotFoundException commandNotFoundException) {
            // Can be ignored, not every message starting with ~ is a command :)
        }
    }
}
