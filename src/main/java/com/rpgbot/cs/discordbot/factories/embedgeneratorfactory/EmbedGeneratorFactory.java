package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.SuccessEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.CommandNameTakenExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.CommandNotFoundExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.ErrorExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.UserNotFoundExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.CreateCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.CreateEmbedCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.EditEmbedHelpCommandEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.HelpCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.ModifyCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.ProfileCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.RegisterCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.RemoveCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.RenameCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.SetColorCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.SetEmbedColorCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.SetEmbedTitleCommandHelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.StaticHelpEmbedGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmbedGeneratorFactory {

    private HashMap<EmbedType, IEmbedGenerator> exceptionEmbeds;
    private HashMap<String, IEmbedGenerator> helpEmbeds;
    private HashMap<EmbedType, IEmbedGenerator> embeds;

    // embeds
    private final SuccessEmbedGenerator successEmbedGenerator;

    // exception embeds
    private final CommandNotFoundExceptionEmbedGenerator commandNotFoundExceptionEmbedGenerator;
    private final UserNotFoundExceptionEmbedGenerator userNotFoundExceptionEmbedGenerator;
    private final CommandNameTakenExceptionEmbedGenerator commandNameTakenExceptionEmbedGenerator;
    private final ErrorExceptionEmbedGenerator errorExceptionEmbedGenerator;

    // help embeds
    private final CreateCommandHelpEmbedGenerator createCommandHelpEmbedGenerator;
    private final HelpCommandHelpEmbedGenerator helpCommandHelpEmbedGenerator;
    private final ModifyCommandHelpEmbedGenerator modifyCommandHelpEmbedGenerator;
    private final ProfileCommandHelpEmbedGenerator profileCommandHelpEmbedGenerator;
    private final RegisterCommandHelpEmbedGenerator registerCommandHelpEmbedGenerator;
    private final RemoveCommandHelpEmbedGenerator removeCommandHelpEmbedGenerator;
    private final SetColorCommandHelpEmbedGenerator setColorCommandHelpEmbedGenerator;
    private final CreateEmbedCommandHelpEmbedGenerator createEmbedCommandHelpEmbedGenerator;
    private final EditEmbedHelpCommandEmbedGenerator editEmbedCommandHelpEmbedGenerator;
    private final SetEmbedColorCommandHelpEmbedGenerator setEmbedColorCommandHelpEmbedGenerator;
    private final SetEmbedTitleCommandHelpEmbedGenerator setEmbedTitleCommandHelpEmbedGenerator;
    private final RenameCommandHelpEmbedGenerator renameCommandHelpEmbedGenerator;
    private final StaticHelpEmbedGenerator staticHelpEmbedGenerator;

    @Autowired
    public EmbedGeneratorFactory(DiscordBotConfiguration discordBotConfiguration, CommandNotFoundExceptionEmbedGenerator commandNotFoundExceptionEmbedGenerator, UserNotFoundExceptionEmbedGenerator userNotFoundExceptionEmbedGenerator, CommandNameTakenExceptionEmbedGenerator commandNameTakenExceptionEmbedGenerator, ErrorExceptionEmbedGenerator errorExceptionEmbedGenerator, CreateCommandHelpEmbedGenerator createCommandHelpEmbedGenerator, HelpCommandHelpEmbedGenerator helpCommandHelpEmbedGenerator, ModifyCommandHelpEmbedGenerator modifyCommandHelpEmbedGenerator, ProfileCommandHelpEmbedGenerator profileCommandHelpEmbedGenerator, RegisterCommandHelpEmbedGenerator registerCommandHelpEmbedGenerator, RemoveCommandHelpEmbedGenerator removeCommandHelpEmbedGenerator, SetColorCommandHelpEmbedGenerator setColorCommandHelpEmbedGenerator, StaticHelpEmbedGenerator staticHelpEmbedGenerator, SuccessEmbedGenerator successEmbedGenerator, CreateEmbedCommandHelpEmbedGenerator createEmbedCommandHelpEmbedGenerator, EditEmbedHelpCommandEmbedGenerator editEmbedCommandHelpEmbedGenerator, SetEmbedColorCommandHelpEmbedGenerator setEmbedColorCommandHelpEmbedGenerator, SetEmbedTitleCommandHelpEmbedGenerator setEmbedTitleCommandHelpEmbedGenerator, RenameCommandHelpEmbedGenerator renameCommandHelpEmbedGenerator) {

        this.commandNotFoundExceptionEmbedGenerator = commandNotFoundExceptionEmbedGenerator;
        this.userNotFoundExceptionEmbedGenerator = userNotFoundExceptionEmbedGenerator;
        this.commandNameTakenExceptionEmbedGenerator = commandNameTakenExceptionEmbedGenerator;
        this.errorExceptionEmbedGenerator = errorExceptionEmbedGenerator;
        this.createCommandHelpEmbedGenerator = createCommandHelpEmbedGenerator;
        this.helpCommandHelpEmbedGenerator = helpCommandHelpEmbedGenerator;
        this.modifyCommandHelpEmbedGenerator = modifyCommandHelpEmbedGenerator;
        this.profileCommandHelpEmbedGenerator = profileCommandHelpEmbedGenerator;
        this.registerCommandHelpEmbedGenerator = registerCommandHelpEmbedGenerator;
        this.removeCommandHelpEmbedGenerator = removeCommandHelpEmbedGenerator;
        this.setColorCommandHelpEmbedGenerator = setColorCommandHelpEmbedGenerator;
        this.staticHelpEmbedGenerator = staticHelpEmbedGenerator;
        this.successEmbedGenerator = successEmbedGenerator;
        this.createEmbedCommandHelpEmbedGenerator = createEmbedCommandHelpEmbedGenerator;
        this.editEmbedCommandHelpEmbedGenerator = editEmbedCommandHelpEmbedGenerator;
        this.setEmbedColorCommandHelpEmbedGenerator = setEmbedColorCommandHelpEmbedGenerator;
        this.setEmbedTitleCommandHelpEmbedGenerator = setEmbedTitleCommandHelpEmbedGenerator;
        this.renameCommandHelpEmbedGenerator = renameCommandHelpEmbedGenerator;

        // embeds
        this.embeds = new HashMap<EmbedType, IEmbedGenerator>();

        embeds.put(EmbedType.SUCCESS, successEmbedGenerator);

        // exception embeds
        this.exceptionEmbeds = new HashMap<EmbedType, IEmbedGenerator>();

        exceptionEmbeds.put(EmbedType.COMMANDNOTFOUNDEXCEPTION, commandNotFoundExceptionEmbedGenerator);
        exceptionEmbeds.put(EmbedType.USERNOTFOUNDEXCEPTION, userNotFoundExceptionEmbedGenerator);
        exceptionEmbeds.put(EmbedType.COMMANDNAMETAKENEXCEPTION,commandNameTakenExceptionEmbedGenerator);
        exceptionEmbeds.put(EmbedType.GENERICERROR, errorExceptionEmbedGenerator);

        // help embeds
        this.helpEmbeds = new HashMap<String, IEmbedGenerator>();

        helpEmbeds.put(discordBotConfiguration.getCreateCommand(), createCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getHelpCommand(), helpCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getModifyCommand(), modifyCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getProfileCommand(), profileCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getRegisterCommand(), registerCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getRemoveCommand(), removeCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getSetColorCommand(), setColorCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getCreateEmbedCommand(), createEmbedCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getSetEmbedDescriptionCommand(), editEmbedCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getSetEmbedColorCommand(), setEmbedColorCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getSetEmbedTitleCommand(), setEmbedTitleCommandHelpEmbedGenerator);
        helpEmbeds.put(discordBotConfiguration.getRenameCommand(), renameCommandHelpEmbedGenerator);
    }

    public IEmbedGenerator get(EmbedType type) {
        if (embeds.containsKey(type)) return embeds.get(type);
        return errorExceptionEmbedGenerator;
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
