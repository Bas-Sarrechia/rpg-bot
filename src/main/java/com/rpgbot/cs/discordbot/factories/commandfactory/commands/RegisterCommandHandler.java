package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class RegisterCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    private final DiscordUserDao discordUserDao;

    @Autowired
    public RegisterCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration, DiscordUserDao discordUserDao) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
        this.discordUserDao = discordUserDao;
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            // attempts to get user, throws user not found exception
            User user = messageCreateEvent.getMessageAuthor().asUser().orElseThrow(() -> new UserNotFoundException());
            // builds DiscordUser from User
            DiscordUser discordUser = DiscordUser.builder()
                    .id(user.getId())
                    .preferredColor(Color.RED)
                    .nickname(messageCreateEvent.getMessageAuthor().getDisplayName())
                    .build();
            this.discordUserDao.save(discordUser);
            messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Success!")
                .setDescription("User \"" + messageCreateEvent.getMessageAuthor().getName() + "\" registered."));
        } catch (UserNotFoundException userNotFoundException) {
            // sends error message
            messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().error(EmbedType.USERNOTFOUNDEXCEPTION).build(userNotFoundException.getMessage()));
        }
    }
}
