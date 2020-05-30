package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.CommandService;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;

@Component
public class CommandRouter implements CommandExecutor {
    private final BotService botService;
    private final CommandService commandService;

    public CommandRouter(BotService botService, CommandService commandService) {
        this.botService = botService;
        this.commandService = commandService;
    }

    @Command(aliases = "!ping")
    private void addCommandListener() {
        this.botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent().strip();
            if (message.startsWith("!addcommand")) {
                String strippedMessage = message.substring("!addcommand".length()).stripLeading();
                int indexOfCommandEnd = strippedMessage.indexOf(' ');
                if (indexOfCommandEnd > -1) {
                    String command = strippedMessage.substring(0, indexOfCommandEnd);
                    String response = strippedMessage.substring(indexOfCommandEnd + 1).stripLeading();

                    commandService.addBasicCommandToBot(command, response);

                    messageCreateEvent.getChannel().sendMessage("♥ Sara, not iffy");
                } else {
                    messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("How to use this command", "!addcommand <command> <respond>")
                            .setFooter("adds a static command to the bot")
                    );
                }
            }
        });
    }
}
