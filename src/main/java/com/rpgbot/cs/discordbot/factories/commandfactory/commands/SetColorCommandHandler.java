package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.beryx.awt.color.ColorFactory;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SetColorCommandHandler implements ICommandHandler {

    private final DiscordUserDao discordUserDao;
    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final DiscordBotConfiguration discordBotConfiguration;

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            DiscordUser discordUser = discordUserDao.findById(messageCreateEvent.getMessageAuthor().getId()).orElseThrow(() -> new UserNotFoundException());
            if (message.split(" ").length > 1) {
                discordUser.setPreferredColor(ColorFactory.web(message.split(" ")[1]));
                discordUserDao.save(discordUser);
                messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.get(EmbedType.SUCCESS).build("Color set: " + message.split(" ")[1]));
            } else {
                messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.getHelp(discordBotConfiguration.getSetColorCommand()).build("Please contact the administrators..."));

            }
        } catch (UserNotFoundException userNotFoundException) {
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.USERNOTFOUNDEXCEPTION).build(messageCreateEvent.getMessageAuthor().getDiscriminatedName().substring(0, messageCreateEvent.getMessageAuthor().getDiscriminatedName().length() - 5)));
        }
    }
}
