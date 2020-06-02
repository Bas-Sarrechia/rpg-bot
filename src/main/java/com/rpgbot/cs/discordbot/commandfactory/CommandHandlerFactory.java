package com.rpgbot.cs.discordbot.commandfactory;

import com.rpgbot.cs.discordbot.commandfactory.commands.CreateCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.commands.ModifyCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.commands.RemoveCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.commands.StaticCommandHandler;
import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.commandfactory.commands.CharactersCommandHandler;
import com.rpgbot.cs.discordbot.commandfactory.commands.RegisterCommandHandler;
import com.rpgbot.cs.discordbot.services.CommandService;
import com.rpgbot.cs.discordbot.services.EmbedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandHandlerFactory {

    private final EmbedService embedService;
    private final CommandService commandService;
    private final DiscordBotConfiguration discordBotConfiguration;

    public AbstractCommandHandler get(String command) {
        switch (command.split(" ")[0]) {
            case "addcommand":
                return new CreateCommandHandler(commandService, embedService, discordBotConfiguration);
            case "modifycommand":
                return new ModifyCommandHandler(commandService, embedService, discordBotConfiguration);
            case "removecommand":
                return new RemoveCommandHandler(commandService, embedService, discordBotConfiguration);
            case "register":
                return new RegisterCommandHandler(commandService, embedService, discordBotConfiguration);
            case "characters":
                return new CharactersCommandHandler(commandService, embedService, discordBotConfiguration);
            default:
                return new StaticCommandHandler(commandService, embedService, discordBotConfiguration);
        }
    }
}
