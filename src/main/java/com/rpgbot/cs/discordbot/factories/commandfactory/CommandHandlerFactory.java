package com.rpgbot.cs.discordbot.factories.commandfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.commandfactory.commands.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class CommandHandlerFactory {

    private HashMap<String, AbstractCommandHandler> commandHandlers;
    private final DiscordBotConfiguration discordBotConfiguration;

    // command handlers
    private final CreateCommandHandler createCommandHandler;
    private final ModifyCommandHandler modifyCommandHandler;
    private final RemoveCommandHandler removeCommandHandler;
    private final RegisterCommandHandler registerCommandHandler;
    private final ProfileCommandHandler profileCommandHandler;
    private final HelpCommandHandler helpCommandHandler;
    private final SetColorCommandHandler setColorCommandHandler;
    private final StaticCommandHandler staticCommandHandler;

    public CommandHandlerFactory(HashMap<String, AbstractCommandHandler> commandHandlers, DiscordBotConfiguration discordBotConfiguration, CreateCommandHandler createCommandHandler, ModifyCommandHandler modifyCommandHandler, RemoveCommandHandler removeCommandHandler, RegisterCommandHandler registerCommandHandler, ProfileCommandHandler profileCommandHandler, HelpCommandHandler helpCommandHandler, SetColorCommandHandler setColorCommandHandler, StaticCommandHandler staticCommandHandler) {
        this.discordBotConfiguration = discordBotConfiguration;

        this.createCommandHandler = createCommandHandler;
        this.modifyCommandHandler = modifyCommandHandler;
        this.removeCommandHandler = removeCommandHandler;
        this.registerCommandHandler = registerCommandHandler;
        this.profileCommandHandler = profileCommandHandler;
        this.helpCommandHandler = helpCommandHandler;
        this.setColorCommandHandler = setColorCommandHandler;
        this.staticCommandHandler = staticCommandHandler;

        this.commandHandlers = new HashMap<String, AbstractCommandHandler>();

    }

    @PostConstruct
    public void init() {
        commandHandlers.put(discordBotConfiguration.getCreateCommand(), createCommandHandler);
        commandHandlers.put(discordBotConfiguration.getHelpCommand(), helpCommandHandler);
        commandHandlers.put(discordBotConfiguration.getModifyCommand(), modifyCommandHandler);
        commandHandlers.put(discordBotConfiguration.getProfileCommand(), profileCommandHandler);
        commandHandlers.put(discordBotConfiguration.getRegisterCommand(), registerCommandHandler);
        commandHandlers.put(discordBotConfiguration.getRemoveCommand(), removeCommandHandler);
        commandHandlers.put(discordBotConfiguration.getSetColorCommand(), setColorCommandHandler);
    }

    // returns CommandHandler for each command
    public AbstractCommandHandler get(String command) {
        if (commandHandlers.containsKey(command)) return commandHandlers.get(command);
        return staticCommandHandler;
    }
}
