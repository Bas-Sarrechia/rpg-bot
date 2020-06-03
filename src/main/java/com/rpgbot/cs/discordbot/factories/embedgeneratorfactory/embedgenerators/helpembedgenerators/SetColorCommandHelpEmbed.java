package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.AbstractEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class SetColorCommandHelpEmbed extends AbstractEmbedGenerator implements IEmbedGenerator {

    @Autowired
    public SetColorCommandHelpEmbed(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    @Override
    public EmbedBuilder build(String message) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(super.getDiscordBotConfiguration().getPrefix() + "setcolor")
                .addField("USAGE", super.getDiscordBotConfiguration().getPrefix() + "setcolor <color>")
                .setFooter("changes the embed color of your " + super.getDiscordBotConfiguration().getPrefix() + "profile");
    }
}
