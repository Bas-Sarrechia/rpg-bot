package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModifyCommandHandler implements ICommandHandler {

    private final CommandService commandService;
    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final DiscordBotConfiguration discordBotConfiguration;

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        // checks for enough args
        if (message.split(" ").length > 2) {
            // gets command name
            String command = message.split(" ")[1];
            // gets command response
            String response = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
            try {
                // modifies command in basicCommandDao, throws CommandNotFoundException
                commandService.modifyStaticCommand(command, response);
                // generates success embed
                messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build("Command modified: " + command));
            } catch (CommandNotFoundException commandNotFoundException) {
                // generates error embed
                messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNOTFOUNDEXCEPTION).build(commandNotFoundException.getMessage()));
            }
        } else {
            // explains how to use command if not enough args
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getModifyCommand()).build("if you're seeing this, please contact an administrator :)"));
        }
    }
}
