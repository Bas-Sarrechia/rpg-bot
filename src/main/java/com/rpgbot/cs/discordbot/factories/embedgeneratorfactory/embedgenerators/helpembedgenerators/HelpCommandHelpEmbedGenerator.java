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
public class HelpCommandHelpEmbedGenerator extends AbstractEmbedGenerator implements IEmbedGenerator {

    @Autowired
    public HelpCommandHelpEmbedGenerator(BasicCommandDao basicCommandDao, DiscordBotConfiguration discordBotConfiguration) {
        super(basicCommandDao, discordBotConfiguration);
    }

    public EmbedBuilder build(String message) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(getDiscordBotConfiguration().getPrefix() + super.getDiscordBotConfiguration().getHelpCommand())
                .addField("USAGE", getDiscordBotConfiguration().getPrefix() + super.getDiscordBotConfiguration().getHelpCommand() + " <command>")
                .setFooter("gives usage instructions for a command");
    }
}
