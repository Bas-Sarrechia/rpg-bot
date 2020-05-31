package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.List;

@Component
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
    private void handleStaticCommand() {
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().strip();
            if (message.startsWith(discordBotConfiguration.getPrefix())) {
                String rawMessage = message.substring(discordBotConfiguration.getPrefix().length()); // Strips the prefix from the message
                String command = rawMessage.split(" ")[0];
                String strippedMessage = rawMessage.substring(command.length()).stripLeading(); // gets rid of initial command
                BasicCommand basicCommand = commandService.lookupCommand(command);
                if (basicCommand != null) {
                    messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
                } else if (command.equals("addcommand")) {
                    int indexOfCommandEnd = strippedMessage.indexOf(' ');
                    if (indexOfCommandEnd > -1) {
                        String newCommand = strippedMessage.substring(0, indexOfCommandEnd);
                        String response = strippedMessage.substring(indexOfCommandEnd + 1).stripLeading();

                        if (commandService.lookupCommand(newCommand) == null) {
                            commandService.registerBasicCommand(newCommand, response);
                            messageCreateEvent.getChannel().sendMessage("Command added: " + newCommand + " - " + response);
                        } else {
                            messageCreateEvent.getChannel().sendMessage("A command with that name already exists.");
                        }
                    } else {
                        messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                               .setColor(Color.RED)
                               .addField("How to use this command", discordBotConfiguration.getPrefix() + "addcommand <command> <respond>")
                               .setFooter("adds a static command to the bot")
                        );
                    }
                } else if (command.equals("removecommand")) {
                    String commandToRemove = strippedMessage.split(" ")[0];
                    commandService.removeCommand(commandToRemove);
                    messageCreateEvent.getChannel().sendMessage("Command removed: " + commandToRemove);
                } else if (command.equals("modifycommand")) {
                    int indexOfCommandEnd = strippedMessage.indexOf(" ");
                    if (indexOfCommandEnd > -1) {
                        String commandToModify = strippedMessage.substring(0, indexOfCommandEnd);
                        String response = strippedMessage.substring(indexOfCommandEnd);
                        commandService.modifyCommand(commandToModify, response);
                        messageCreateEvent.getChannel().sendMessage("Commmand modified: " + commandToModify + " - " + response);
                    } else {
                        messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("How to use this command", discordBotConfiguration.getPrefix() + "modifycommand <command> <respond>")
                                .setFooter("modifies a static command in the bot")
                        );
                    }
                }
           }
        });
    }




}
