package com.rpgbot.cs.discordbot.factories.commandfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;

@Getter
@RequiredArgsConstructor
public class AbstractCommandHandler {

    private final CommandService commandService;
    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final DiscordBotConfiguration discordBotConfiguration;

    public void handle(MessageCreateEvent messageCreateEvent, String message){}

}
