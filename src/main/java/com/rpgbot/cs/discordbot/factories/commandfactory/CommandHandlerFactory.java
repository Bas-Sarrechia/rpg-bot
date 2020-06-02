package com.rpgbot.cs.discordbot.factories.commandfactory;

import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.commandfactory.commands.*;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.EmbedGeneratorFactory;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandHandlerFactory {

    private final CommandService commandService;
    private final DiscordBotConfiguration discordBotConfiguration;
    private final EmbedGeneratorFactory embedGeneratorFactory;
    private final BasicCommandDao basicCommandDao;

    public AbstractCommandHandler get(String command) {
        switch (command.split(" ")[0]) {
            case "addcommand":
                return new CreateCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "modifycommand":
                return new ModifyCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "removecommand":
                return new RemoveCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "register":
                return new RegisterCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "characters":
                return new CharactersCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            case "help":
                return new HelpCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
            default:
                return new StaticCommandHandler(commandService, embedGeneratorFactory, discordBotConfiguration);
        }
    }
}
