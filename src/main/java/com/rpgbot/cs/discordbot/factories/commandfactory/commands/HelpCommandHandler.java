package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.event.message.MessageCreateEvent;

public class HelpCommandHandler  extends AbstractCommandHandler implements ICommandHandler {
    public HelpCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().get(EmbedType.HELP).build(message.split(" ")[1]));
    }
}
