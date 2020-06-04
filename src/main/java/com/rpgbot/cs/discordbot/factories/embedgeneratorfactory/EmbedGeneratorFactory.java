package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.*;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmbedGeneratorFactory {

    private HashMap<EmbedType, IEmbedGenerator> exceptionEmbeds;
    private HashMap<String, IEmbedGenerator> helpEmbeds;
    private final DiscordBotConfiguration discordBotConfiguration;

    // exception embeds
    private final CommandNotFoundExceptionEmbedGenerator commandNotFoundExceptionEmbedGenerator;
    private final UserNotFoundExceptionEmbedGenerator userNotFoundExceptionEmbedGenerator;
    private final ErrorExceptionEmbedGenerator errorExceptionEmbedGenerator;

    // help embeds
    private final CreateCommandHelpEmbedGenerator createCommandHelpEmbedGenerator;
    private final HelpCommandHelpEmbedGenerator helpCommandHelpEmbedGenerator;
    private final ModifyCommandHelpEmbedGenerator modifyCommandHelpEmbedGenerator;
    private final ProfileCommandHelpEmbedGenerator profileCommandHelpEmbedGenerator;
    private final RegisterCommandHelpEmbedGenerator registerCommandHelpEmbedGenerator;
    private final RemoveCommandHelpEmbedGenerator removeCommandHelpEmbedGenerator;
    private final SetColorCommandHelpEmbedGenerator setColorCommandHelpEmbedGenerator;
    private final StaticHelpEmbedGenerator staticHelpEmbedGenerator;

    @Autowired
    public EmbedGeneratorFactory(DiscordBotConfiguration discordBotConfiguration, CommandNotFoundExceptionEmbedGenerator commandNotFoundExceptionEmbedGenerator, UserNotFoundExceptionEmbedGenerator userNotFoundExceptionEmbedGenerator, ErrorExceptionEmbedGenerator errorExceptionEmbedGenerator, CreateCommandHelpEmbedGenerator createCommandHelpEmbedGenerator, HelpCommandHelpEmbedGenerator helpCommandHelpEmbedGenerator, ModifyCommandHelpEmbedGenerator modifyCommandHelpEmbedGenerator, ProfileCommandHelpEmbedGenerator profileCommandHelpEmbedGenerator, RegisterCommandHelpEmbedGenerator registerCommandHelpEmbedGenerator, RemoveCommandHelpEmbedGenerator removeCommandHelpEmbedGenerator, SetColorCommandHelpEmbedGenerator setColorCommandHelpEmbedGenerator, StaticHelpEmbedGenerator staticHelpEmbedGenerator) {

        this.discordBotConfiguration = discordBotConfiguration;
        this.commandNotFoundExceptionEmbedGenerator = commandNotFoundExceptionEmbedGenerator;
        this.userNotFoundExceptionEmbedGenerator = userNotFoundExceptionEmbedGenerator;
        this.errorExceptionEmbedGenerator = errorExceptionEmbedGenerator;
        this.createCommandHelpEmbedGenerator = createCommandHelpEmbedGenerator;
        this.helpCommandHelpEmbedGenerator = helpCommandHelpEmbedGenerator;
        this.modifyCommandHelpEmbedGenerator = modifyCommandHelpEmbedGenerator;
        this.profileCommandHelpEmbedGenerator = profileCommandHelpEmbedGenerator;
        this.registerCommandHelpEmbedGenerator = registerCommandHelpEmbedGenerator;
        this.removeCommandHelpEmbedGenerator = removeCommandHelpEmbedGenerator;
        this.setColorCommandHelpEmbedGenerator = setColorCommandHelpEmbedGenerator;
        this.staticHelpEmbedGenerator = staticHelpEmbedGenerator;

        // exception embeds
        this.exceptionEmbeds = new HashMap<EmbedType, IEmbedGenerator>();

        exceptionEmbeds.put(EmbedType.COMMANDNOTFOUNDEXCEPTION, commandNotFoundExceptionEmbedGenerator);
        exceptionEmbeds.put(EmbedType.USERNOTFOUNDEXCEPTION, userNotFoundExceptionEmbedGenerator);

        // help embeds
        this.helpEmbeds = new HashMap<String, IEmbedGenerator>();

        helpEmbeds.put(discordBotConfiguration.getCreateCommand(), createCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getHelpCommand(), helpCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getModifyCommand(), modifyCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getProfileCommand(), profileCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getRegisterCommand(), registerCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getRemoveCommand(), removeCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getSetColorCommand(), setColorCommandHelpEmbedGenerator);
    }

    public IEmbedGenerator error(EmbedType type) {
        if (exceptionEmbeds.containsKey(type)) return exceptionEmbeds.get(type);
        return errorExceptionEmbedGenerator;
    }

    public IEmbedGenerator getHelp(String command) {
        if (helpEmbeds.containsKey(command)) return helpEmbeds.get(command);
        return staticHelpEmbedGenerator;
    }

}
