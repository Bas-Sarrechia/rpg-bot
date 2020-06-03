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
public class CreateCommandHelpEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {

    @Autowired
    public CreateCommandHelpEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }


    public EmbedBuilder build(String command) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(super.getDiscordBotConfiguration().getPrefix() + super.getDiscordBotConfiguration().getCreateCommand())
                .addField("USAGE", super.getDiscordBotConfiguration().getPrefix() + super.getDiscordBotConfiguration().getCreateCommand() + " <command> <response>")
                .setFooter("adds a static command to the bot");
    }
}
