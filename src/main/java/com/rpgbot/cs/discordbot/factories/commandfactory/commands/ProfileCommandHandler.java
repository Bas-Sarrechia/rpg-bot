package com.rpgbot.cs.discordbot.factories.commandfactory.commands;

import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.exceptions.UserNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.AbstractCommandHandler;
import com.rpgbot.cs.discordbot.factories.commandfactory.ICommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedType;
import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    private final DiscordUserDao discordUserDao;
    private final BotService botService;

    @Autowired
    public ProfileCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration, DiscordUserDao discordUserDao, BotService botService) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
        this.discordUserDao = discordUserDao;
        this.botService = botService;
    }

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
                userEmbed.setAuthor(user.getDiscriminatedName().substring(0, user.getDiscriminatedName().length() - 5), "", user.getAvatar());


                messageCreateEvent.getChannel().sendMessage(userEmbed);
            });
        } catch (UserNotFoundException userNotFoundException) {
            // sends user not found embed
            messageCreateEvent.getChannel().sendMessage(super.getEmbedGeneratorFactory().error(EmbedType.USERNOTFOUNDEXCEPTION).build(messageCreateEvent.getMessageAuthor().getDiscriminatedName()));
        }
    }
}
