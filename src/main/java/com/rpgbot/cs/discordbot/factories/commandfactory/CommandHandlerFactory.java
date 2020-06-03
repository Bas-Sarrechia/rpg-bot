package com.rpgbot.cs.discordbot.factories.commandfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.DiscordUserDao;
import com.rpgbot.cs.discordbot.factories.commandfactory.commands.*;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.springframework.stereotype.Component;

@Component
public class CommandHandlerFactory {

    private final CommandService commandService;
    private final DiscordBotConfiguration discordBotConfiguration;
    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final DiscordUserDao discordUserDao;
    private final BotService botService;

    public CommandHandlerFactory(CommandService commandService, DiscordBotConfiguration discordBotConfiguration, EmbedGeneratorFactory embedGeneratorFactory, DiscordUserDao discordUserDao, BotService botService) {
        this.commandService = commandService;
        this.discordBotConfiguration = discordBotConfiguration;
        this.embedGeneratorFactory = embedGeneratorFactory;
        this.discordUserDao = discordUserDao;
        this.botService = botService;
    }

    // returns CommandHandler for each command
    public AbstractCommandHandler get(String command) {
        // gets command, returns appropriate CommandHandler
        switch (command) {
            case "addcommand":
                return new CreateCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "modifycommand":
                return new ModifyCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "removecommand":
                return new RemoveCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "register":
                return new RegisterCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration, discordUserDao);
            case "profile":
                return new ProfileCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration, discordUserDao, botService);
            case "help":
                return new HelpCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "setcolor":
                return new SetColorCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration, discordUserDao);
            default:
                return new StaticCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
        }
    }
}
