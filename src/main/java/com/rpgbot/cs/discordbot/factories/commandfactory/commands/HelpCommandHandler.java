package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommandHandler  extends AbstractCommandHandler implements ICommandHandler {

    @Autowired
    public HelpCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        if (message.split(" ").length > 1) {
            messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().getHelp(message.split(" ")[1]).build(message.split(" ")[1]));
        } else {
            messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().getHelp("help").build("if ur seeing this, contact an administrator:)"));
        }
    }
}
