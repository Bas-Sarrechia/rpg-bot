package com.rpgbot.cs.discordbot.router;

import com.rpgbot.cs.discordbot.annotations.Command;
import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.exception.CommandExistsException;
import com.rpgbot.cs.discordbot.exception.CommandNotExistsException;
import com.rpgbot.cs.discordbot.messagehandlers.DiscordMessage;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DbCommandRouter {

    private final CommandService commandService;

    @Autowired
    public DbCommandRouter(CommandService commandService) {
        this.commandService = commandService;
    }

    @Order(-1)
    @Command
    public DiscordMessage dbCommand(final CommandMessageEvent commandMessageEvent) {
        return commandService.lookUp(commandMessageEvent.getCommand())
                .map(basicCommand -> DiscordMessage.plain(basicCommand.getResponse())).orElse(null);
    }

    @Command(alias = "addcommand")
    public DiscordMessage addCommand(final CommandMessageEvent commandMessageEvent) {
        if (commandMessageEvent.getArgs().length >= 2) {
            try {
                commandService.register(commandMessageEvent.getArgs()[0], String.join(" ", Arrays.copyOfRange(commandMessageEvent.getArgs(), 1, commandMessageEvent.getArgs().length)));
            } catch (CommandExistsException commandExistsException) {
                return DiscordMessage.error(commandExistsException.getMessage());
            }
        } else {
            return DiscordMessage.error("addcommand <command> <respond>", "adds a static command to the bot");
        }
        return DiscordMessage.plain("command added");
    }

    @Command(alias = "modifycommand")
    public DiscordMessage editCommand(final CommandMessageEvent commandMessageEvent) {
        if (commandMessageEvent.getArgs().length >= 2) {
            try {
                commandService.modifyCommand(commandMessageEvent.getArgs()[0], String.join(" ", Arrays.copyOfRange(commandMessageEvent.getArgs(), 1, commandMessageEvent.getArgs().length)));
            } catch (CommandNotExistsException commandNotExistsException) {
                return DiscordMessage.error(commandNotExistsException.getMessage());
            }
        }
        return DiscordMessage.plain("command modified");
    }
}
