package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommandRouter {
    private final BotService botService;
    private final CommandService commandService;
    private final DiscordBotConfiguration discordBotConfiguration;
    private final EmbedService embedService;


    // Creates listener for Help Command
    @PostConstruct
    public void handleHelpCommand() {

        String commandName = "help";

        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
           // Strips message of any leading whitespace and lowers case
           String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
           if (validateInput(message)) {
               //  Checks if command == "help"
               if (getCommand(message).equals(commandName)) {
                   // Checks if command has an argument
                   if (message.split(" ").length > 1) {
                       try {
                           // Attempts to create help embed for message, throws commandNotFoundException
                           messageCreateEvent.getChannel().sendMessage(embedService.generateBasicHelpEmbed(message.split(" ")[1]));
                       } catch (CommandNotFoundException commandNotFoundException) {
                           // Sends an error message to the chat
                            messageCreateEvent.getChannel().sendMessage(embedService.generateExceptionEmbed(commandNotFoundException, ExceptionType.COMMANDNOTFOUND));
                       }
                   } else {
                       // If there isn't an argument, explains how to use it
                       messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                               .setColor(Color.RED)
                               .addField("How to use this command", discordBotConfiguration.getPrefix() + commandName + " <command> <description>")
                               .setFooter("gets info on the command")
                       );
                   }
               }
           }
        });
    }

    // Creates listener for ~setdesc
    @PostConstruct
    public void setBasicCommandDescription() {
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {

            String commandName = "setdesc";

            // Strips message of any leading whitespace and lowers case
            String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
           if (validateInput(message)) {
               // Checks if command is setdesc
               if (getCommand(message).equals(commandName)) {
                   // Checks if there are at least two arguments
                   if (message.split(" ").length > 2) {
                       // Sets "command" to the command we're modifying
                       String command = message.split(" ")[1];
                       // Sets description to everything after that
                       String description = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
                        try {
                            // Attempts to set command description, throws CommandNotFoundException
                            commandService.setCommandDescription(command, description);
                            messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                                    .setTitle("Success!")
                                    .setDescription("Description of \"" + command + "\" set.")
                            );
                        } catch (CommandNotFoundException commandNotFoundException) {
                            // Generates error message
                            messageCreateEvent.getChannel().sendMessage(embedService.generateExceptionEmbed(commandNotFoundException, ExceptionType.COMMANDNOTFOUND));
                        }
                   } else {
                       // If there aren't enough arguments, explains how to use the command
                       messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                               .setColor(Color.RED)
                               .addField("How to use this command", discordBotConfiguration.getPrefix() + commandName + " <command> <description>")
                               .setFooter("adds a description to the command")
                       );
                   }
               }
           }
        });
    }

    //TODO IFFY PLEASE EXPLAIN WHAT THIS IS IT SCARES ME IDK WHAT IT IS
    @PostConstruct
    private void register(){
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
            if (validateInput(message)) {
                if (getCommand(message).equals("register")) {
                    Optional<User> discordUser = messageCreateEvent.getMessageAuthor().asUser();
                    if(discordUser.isPresent()){
                        User user = discordUser.get();
                        DiscordUser.builder()
                                .id(user.getId())
                                .preferredColor(Color.pink)
                                .nickname("")
                                .build();
                    }
                }
            }

        });
    }

    //TODO IFFY COMMENT THIS STUFFF
    @PostConstruct
    private void addCharacterSelectionMenu(){
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().toLowerCase().stripLeading();
            if (validateInput(message)) {
                if (getCommand(message).equals("characters")) {
                    messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setAuthor("Hi")
                );

                }
            }
        });
    }

    // registers a command to the BasicCommandDao
    @PostConstruct
    public void createStaticCommand() {

        String commandName = "addcommand";
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            // if you don't recognise this by now, that's your fault. scroll up
            String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
            if (validateInput(message)) {
                if (getCommand(message).equals(commandName)) {
                    // verifies there's enough arguments
                    if (message.split(" ").length > 2) {
                        // gets the command name
                        String command = message.split(" ")[1];
                        // gets the command response
                        String response = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
                        // registers command to dao
                        commandService.registerBasicCommand(command, response);
                        // success!
                        messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                                .setTitle("Success!")
                                .setDescription("Command added: " + command)
                        );
                    } else {
                        // if there aren't enough arguments, lets the member know how to use the command
                        messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("How to use this command", discordBotConfiguration.getPrefix() + commandName + " <command> <respond>")
                                .setFooter("adds a static command to the bot")
                        );
                    }
                }
            }
        });
    }

    // allows the modification of a pre-existing static/basic command
    @PostConstruct
    public void updateStaticCommand() {

        String commandName = "modifycommand";

        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
            if (validateInput(message)) {
                if (getCommand(message).equals(commandName)) {
                    // checks for enough args
                    if (message.split(" ").length > 2) {
                        // gets command name
                        String command = message.split(" ")[1];
                        // gets command response
                        String response = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
                        try {
                            // modifies command in basicCommandDao, throws CommandNotFoundException
                            commandService.modifyCommand(command, response);
                        } catch (CommandNotFoundException commandNotFoundException) {
                            // generates error embed
                            messageCreateEvent.getChannel().sendMessage(embedService.generateExceptionEmbed(commandNotFoundException, ExceptionType.COMMANDNOTFOUND));
                        }
                    } else {
                        // explains how to use command if not enough args
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

    // removes a static/basic command from the basicCommandDao
    @PostConstruct
    public void deleteStaticCommand() {

        String commandName = "removecommand";

        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
            if (validateInput(message)) {
                if (getCommand(message).equals(commandName)) {
                    // checks that there's at least one argument
                    if (message.indexOf(" ") > -1) {
                        try {
                            // removes command from basicCommandDao, throws CommandNotFoundException
                            commandService.removeCommand(message.split(" ")[1]);
                            // success!
                            messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                                    .setTitle("Success!")
                                    .setDescription("Command removed.")
                            );
                        } catch (CommandNotFoundException commandNotFoundException) {
                            // command not found error
                            messageCreateEvent.getChannel().sendMessage(embedService.generateExceptionEmbed(commandNotFoundException, ExceptionType.COMMANDNOTFOUND));
                        }
                    }
                }
            }
        });
    }

    // gets response from static/basic commands
    @PostConstruct
    public void getStaticCommand() {
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().stripLeading().toLowerCase();
            if (validateInput(message)) {
                try {
                    // looks up command in basicCommandDao
                    BasicCommand basicCommand = commandService.lookupCommand(getCommand(message));
                    messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
                } catch (CommandNotFoundException commandNotFoundException) {
                    // Can be ignored, not every message starting with ~ is a command :)
                }
            }
        });
    }

    // checks that a message begins with prefix
    private boolean validateInput(String input) {
        return input.startsWith(discordBotConfiguration.getPrefix());
    }

    // gets command from message
    private String getCommand(String input) {
        String message = input.split(" ")[0];
        if (message.length() > discordBotConfiguration.getPrefix().length()) {
            return message.substring(discordBotConfiguration.getPrefix().length());
        }
        return discordBotConfiguration.getPrefix();
    }

}
