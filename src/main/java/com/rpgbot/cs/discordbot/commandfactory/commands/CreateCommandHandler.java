package com.rpgbot.cs.discordbot.commandfactory.commands;

import com.rpgbot.cs.discordbot.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;

@Component
public class CreateCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    public CreateCommandHandler(CommandService commandService, EmbedService embedService, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedService, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        // verifies there's enough arguments
        if (message.split(" ").length > 2) {
            // gets the command name
            String command = message.split(" ")[1];
            // gets the command response
            String response = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
            // registers command to dao
            super.getCommandService().registerBasicCommand(command, response);
            // success!
            messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("Success!")
                    .setDescription("Command added: " + command)
                    .setColor(Color.GREEN)
            );
        } else {
            // if there aren't enough arguments, lets the member know how to use the command
            messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(Color.RED)
                    .addField("How to use this command", super.getDiscordBotConfiguration().getPrefix() + message.split(" ")[0] + " <command> <respond>")
                    .setFooter("adds a static command to the bot")
            );
        }
    }

}
