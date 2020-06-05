package com.rpgbot.cs.discordbot.factories.commandfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import com.rpgbot.cs.discordbot.factories.commandfactory.commands.*;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class CommandHandlerFactory {

    private HashMap<String, ICommandHandler> commandHandlers;
    private final DiscordBotConfiguration discordBotConfiguration;
    private final CommandService commandService;

    // command handlers
    private final CreateCommandHandler createCommandHandler;
    private final ModifyCommandHandler modifyCommandHandler;
    private final RemoveCommandHandler removeCommandHandler;
    private final RegisterCommandHandler registerCommandHandler;
    private final ProfileCommandHandler profileCommandHandler;
    private final HelpCommandHandler helpCommandHandler;
    private final SetColorCommandHandler setColorCommandHandler;
    private final CreateEmbedCommandHandler createEmbedCommandHandler;
    private final EditEmbedCommandHandler editEmbedCommandHandler;
    private final SetEmbedColorCommandHandler setEmbedColorCommandHandler;
    private final SetEmbedTitleCommandHandler setEmbedTitleCommandHandler;
    private final RenameCommandCommandHandler renameCommandCommandHandler;
    private final StaticCommandHandler staticCommandHandler;
    private final EmbedCommandHandler embedCommandHandler;

    public CommandHandlerFactory(HashMap<String, ICommandHandler> commandHandlers, DiscordBotConfiguration discordBotConfiguration, CommandService commandService, CreateCommandHandler createCommandHandler, ModifyCommandHandler modifyCommandHandler, RemoveCommandHandler removeCommandHandler, RegisterCommandHandler registerCommandHandler, ProfileCommandHandler profileCommandHandler, HelpCommandHandler helpCommandHandler, SetColorCommandHandler setColorCommandHandler, CreateEmbedCommandHandler createEmbedCommandHandler, EditEmbedCommandHandler editEmbedCommandHandler, SetEmbedColorCommandHandler setEmbedColorCommandHandler, SetEmbedTitleCommandHandler setEmbedTitleCommandHandler, RenameCommandCommandHandler renameCommandCommandHandler, StaticCommandHandler staticCommandHandler, EmbedCommandHandler embedCommandHandler) {
        this.discordBotConfiguration = discordBotConfiguration;
        this.commandService = commandService;

        this.createCommandHandler = createCommandHandler;
        this.modifyCommandHandler = modifyCommandHandler;
        this.removeCommandHandler = removeCommandHandler;
        this.registerCommandHandler = registerCommandHandler;
        this.profileCommandHandler = profileCommandHandler;
        this.helpCommandHandler = helpCommandHandler;
        this.setColorCommandHandler = setColorCommandHandler;
        this.createEmbedCommandHandler = createEmbedCommandHandler;
        this.editEmbedCommandHandler = editEmbedCommandHandler;
        this.setEmbedColorCommandHandler = setEmbedColorCommandHandler;
        this.setEmbedTitleCommandHandler = setEmbedTitleCommandHandler;
        this.renameCommandCommandHandler = renameCommandCommandHandler;
        this.staticCommandHandler = staticCommandHandler;
        this.embedCommandHandler = embedCommandHandler;

        this.commandHandlers = new HashMap<String, ICommandHandler>();

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
        commandHandlers.put(discordBotConfiguration.getCreateEmbedCommand(), createEmbedCommandHandler);
        commandHandlers.put(discordBotConfiguration.getSetEmbedDescriptionCommand(), editEmbedCommandHandler);
        commandHandlers.put(discordBotConfiguration.getSetEmbedTitleCommand(), setEmbedTitleCommandHandler);
        commandHandlers.put(discordBotConfiguration.getSetEmbedColorCommand(), setEmbedColorCommandHandler);
        commandHandlers.put(discordBotConfiguration.getRenameCommand(), renameCommandCommandHandler);
    }

    // returns CommandHandler for each command
    public ICommandHandler get(String command) {
        if (commandHandlers.containsKey(command)) return commandHandlers.get(command);
        try {
            commandService.lookupStaticCommand(command);
            return staticCommandHandler;
        } catch (CommandNotFoundException commandNotFoundException) {
            return embedCommandHandler;
        }
    }
}
