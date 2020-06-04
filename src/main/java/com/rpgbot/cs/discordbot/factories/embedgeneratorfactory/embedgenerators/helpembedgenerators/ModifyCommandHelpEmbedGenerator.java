package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.IEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModifyCommandHelpEmbedGenerator implements IEmbedGenerator {

    private final DiscordBotConfiguration discordBotConfiguration;

    public EmbedBuilder build(String message) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(discordBotConfiguration.getPrefix() + discordBotConfiguration.getModifyCommand())
                .addField("USAGE", discordBotConfiguration.getPrefix() + discordBotConfiguration.getModifyCommand() + " <command> <respond>")
                .setFooter("modifies a static command in the bot");
    }
}
