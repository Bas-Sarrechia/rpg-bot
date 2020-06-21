package com.rpgbot.cs.discordbot.router;

import com.rpgbot.cs.discordbot.annotations.Command;
import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.exception.CommandExistsException;
import com.rpgbot.cs.discordbot.services.CommandService;
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
    public void dbCommand(final CommandMessageEvent commandMessageEvent) {
        commandService.lookUp(commandMessageEvent.getCommand())
                .ifPresent(basicCommand -> commandMessageEvent.getTarget().sendMessage(basicCommand.getResponse()));
    }

    @Command(alias = "addcommand")
    public void addCommand(final CommandMessageEvent commandMessageEvent) {
        if (commandMessageEvent.getCommand().equals("addcommand")) {
            if (commandMessageEvent.getArgs().length >= 2) {
                try {
                    commandService.register(commandMessageEvent.getArgs()[0], String.join(" ", Arrays.copyOfRange(commandMessageEvent.getArgs(), 1, commandMessageEvent.getArgs().length)));
                    commandMessageEvent.getTarget().sendMessage("command added");
                } catch (CommandExistsException commandExistsException) {
                    commandMessageEvent.sendError(commandMessageEvent.getArgs()[0] + " already exists!");
                }
            } else {
                commandMessageEvent.sendError("addcommand <command> <respond>", "adds a static command to the bot");
            }
        }
    }
}
