package com.rpgbot.cs.discordbot.commandfactory.commands;

import com.rpgbot.cs.discordbot.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;

@Component

public class ModifyCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    public ModifyCommandHandler(CommandService commandService, EmbedService embedService, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedService, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        // checks for enough args
        if (message.split(" ").length > 2) {
            // gets command name
            String command = message.split(" ")[1];
            // gets command response
            String response = String.join(" ", Arrays.copyOfRange(message.split(" "), 2, message.split(" ").length));
            try {
                // modifies command in basicCommandDao, throws CommandNotFoundException
                super.getCommandService().modifyCommand(command, response);
                // generates success embed
                messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Success!")
                        .setDescription("Command added: " + command));
            } catch (CommandNotFoundException commandNotFoundException) {
                // generates error embed
                messageCreateEvent.getChannel().sendMessage(super.getEmbedService().generateExceptionEmbed(commandNotFoundException, ExceptionType.COMMANDNOTFOUND));
            }
        } else {
            // explains how to use command if not enough args
            messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(Color.RED)
                    .addField("How to use this command", super.getDiscordBotConfiguration().getPrefix() + "modifycommand <command> <respond>")
                    .setFooter("modifies a static command in the bot")
            );
        }
    }
}
