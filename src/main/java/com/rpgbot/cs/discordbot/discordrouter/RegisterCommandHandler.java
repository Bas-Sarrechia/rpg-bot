package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class RegisterCommandHandler extends AbstractCommandHandler implements ICommandHandler {
    public RegisterCommandHandler(CommandService commandService, EmbedService embedService, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedService, discordBotConfiguration);
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            // attempts to get user, throws user not found exception
            User user = messageCreateEvent.getMessageAuthor().asUser().orElseThrow(() -> new UserNotFoundException());
            // builds DiscordUser from User
            DiscordUser.builder()
                    .id(user.getId())
                    .preferredColor(Color.PINK)
                    .nickname("")
                    .build();
        } catch (UserNotFoundException userNotFoundException) {
            // sends error message
            messageCreateEvent.getChannel().sendMessage(super.getEmbedService().generateExceptionEmbed(userNotFoundException, ExceptionType.COMMANDNOTFOUND));
        }
    }
}
