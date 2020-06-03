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
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.beryx.awt.color.ColorFactory;

import java.awt.*;

@Component
public class SetColorCommandHandler extends AbstractCommandHandler implements ICommandHandler {

    private final DiscordUserDao discordUserDao;

    @Autowired
    public SetColorCommandHandler(CommandService commandService, EmbedGeneratorFactory embedGeneratorFactory, DiscordBotConfiguration discordBotConfiguration, DiscordUserDao discordUserDao) {
        super(commandService, embedGeneratorFactory, discordBotConfiguration);
        this.discordUserDao = discordUserDao;
    }

    @Override
    public void handle(MessageCreateEvent messageCreateEvent, String message) {
        try {
            DiscordUser discordUser = discordUserDao.findById(messageCreateEvent.getMessageAuthor().getId()).orElseThrow(() -> new UserNotFoundException());
            if (message.split(" ").length > 1) {
                discordUser.setPreferredColor(ColorFactory.web(message.split(" ")[1]));
                discordUserDao.save(discordUser);
                messageCreateEvent.getChannel().sendMessage(new EmbedBuilder().setTitle("Success!").setDescription("Color set to " + message.split(" ")[1]).setColor(Color.GREEN));
            } else {
                messageCreateEvent.getChannel().sendMessage(
                        new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("How to use this command", super.getDiscordBotConfiguration().getPrefix() + "setcolor <color>")
                                .setFooter("adds a color to your profile"));

            }
        } catch (UserNotFoundException userNotFoundException) {
            messageCreateEvent.getChannel().sendMessage(getEmbedGeneratorFactory().get(EmbedType.USERNOTFOUNDEXCEPTION).build(messageCreateEvent.getMessageAuthor().getDiscriminatedName().substring(0, messageCreateEvent.getMessageAuthor().getDiscriminatedName().length() - 5)));
        }
    }
}
