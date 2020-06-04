package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelpCommandHandler implements ICommandHandler {

    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final DiscordBotConfiguration discordBotConfiguration;

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        if (message.split(" ").length > 1) {
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(message.split(" ")[1]).build(message.split(" ")[1]));
        } else {
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getHelpCommand()).build("if ur seeing this, contact an administrator:)"));
        }
    }
}
