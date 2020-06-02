package com.rpgbot.cs.discordbot.commandfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.event.message.MessageCreateEvent;

@Getter
@RequiredArgsConstructor
public class AbstractCommandHandler {

    private final CommandService commandService;
    private final EmbedService embedService;
    private final DiscordBotConfiguration discordBotConfiguration;

    public void handle(MessageCreateEvent messageCreateEvent, String message){}

}
