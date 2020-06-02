package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class RegisterCommandHandler extends AbstractCommandHandler implements ICommandHandler {
    public RegisterCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
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
            messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().get(EmbedType.USERNOTFOUNDEXCEPTION).build(userNotFoundException.getMessage()));
        }
    }
}
