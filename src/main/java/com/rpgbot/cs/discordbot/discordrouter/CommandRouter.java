package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.exception.CommandException;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.List;

@Controller
public class CommandRouter {
    private final BotService botService;
    private final CommandService commandService;
    private final DiscordBotConfiguration discordBotConfiguration;


    public CommandRouter(BotService botService, CommandService commandService, DiscordBotConfiguration discordBotConfiguration) {
        this.botService = botService;
        this.commandService = commandService;
        this.discordBotConfiguration = discordBotConfiguration;
    }

    @PostConstruct
    private void addCommandListener() {
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().strip();
            if (message.startsWith("!addcommand")) {
                String strippedMessage = message.substring("!addcommand".length()).stripLeading();
                int indexOfCommandEnd = strippedMessage.indexOf(' ');
                if (indexOfCommandEnd > -1) {
                    String command = strippedMessage.substring(0, indexOfCommandEnd);
                    String response = strippedMessage.substring(indexOfCommandEnd + 1).stripLeading();
                    commandService.addBasicCommandToBot(command, response);
                    messageCreateEvent.getChannel().sendMessage("♥ Sara, not iffy");
                } else {
                    throw new CommandException(messageCreateEvent.getChannel(), "!addcommand <command> <respond>", "adds a static command to the bot");
                }
            }
        });
    }

    @PostConstruct
    private void addStaticCommandListener() {
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().strip();
            if (message.startsWith("!")) {
                BasicCommand basicCommand = commandService.lookUpCommand(message.split(" ")[0]);
                if (basicCommand != null) {
                    messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
                }
            }
        });
    }
}
