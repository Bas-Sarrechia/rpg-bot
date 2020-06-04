package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterCommandHandler implements ICommandHandler {

    private final DiscordUserDao discordUserDao;
    private final EmbedGeneratorFactory embedGeneratorFactory;

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
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build("User registered: " + messageCreateEvent.getMessageAuthor().getName()));
        } catch (UserNotFoundException userNotFoundException) {
            // sends error message
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.USERNOTFOUNDEXCEPTION).build(userNotFoundException.getMessage()));
        }
    }
}
