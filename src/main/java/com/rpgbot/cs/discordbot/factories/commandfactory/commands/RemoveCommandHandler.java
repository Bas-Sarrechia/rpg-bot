package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemoveCommandHandler implements ICommandHandler {

    private final CommandService commandService;
    private final EmbedGeneratorFactory embedGeneratorFactory;

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        // checks that there's at least one argument
        if (message.indexOf(" ") > -1) {
            try {
                // removes command from basicCommandDao, throws CommandNotFoundException
                commandService.removeStaticCommand(message.split(" ")[1]);
                // success!
                messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build("Command removed: " + message.split(" ")[1]));
            } catch (CommandNotFoundException commandNotFoundException) {
                // command not found error
                messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.COMMANDNOTFOUNDEXCEPTION).build(commandNotFoundException.getMessage()));
            }
        }
    }
}
