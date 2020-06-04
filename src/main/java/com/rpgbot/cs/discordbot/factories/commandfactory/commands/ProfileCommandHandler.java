package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.BotService;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileCommandHandler implements ICommandHandler {

    private final DiscordUserDao discordUserDao;
    private final BotService botService;
    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final DiscordBotConfiguration discordBotConfiguration;

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            // gets DiscordUser object from DiscordUserDao, throws UserNotFoundException
            DiscordUser discordUser = discordUserDao.findById(messageCreateEvent.getMessageAuthor().getId()).orElseThrow(() -> new UserNotFoundException());
            // creates new embed, sets color to the user's preferred color
            EmbedBuilder userEmbed = new EmbedBuilder().setColor(discordUser.getPreferredColor());
            // gets javacord User object using the id
            this.botService.getDiscordApi().getUserById(discordUser.getId()).thenAcceptAsync(user -> {
                // sets author and picture, then sends embed
                userEmbed.setAuthor(messageCreateEvent.getMessageAuthor().getName(), "", user.getAvatar());


                messageCreateEvent.getChannel().sendMessage(userEmbed);
            });
        } catch (UserNotFoundException userNotFoundException) {
            // sends user not found embed
            messageCreateEvent.getChannel().sendMessage(embedGeneratorFactory.error(EmbedType.USERNOTFOUNDEXCEPTION).build(messageCreateEvent.getMessageAuthor().getDiscriminatedName()));
        }
    }
}
