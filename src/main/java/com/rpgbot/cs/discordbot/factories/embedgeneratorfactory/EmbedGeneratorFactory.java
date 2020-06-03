package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.CommandNotFoundExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.ErrorExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.helpembedgenerators.*;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.exceptionembedgenerators.UserNotFoundExceptionEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmbedGeneratorFactory {
    private final BasicCommandDao basicCommandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public AbstractEmbedGenerator get(EmbedType embedType) {
        // switch based on embed type, returns EmbedGenerator for each type of embed
        switch (embedType) {
            case HELPCOMMAND:
                return new StaticHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case COMMANDNOTFOUNDEXCEPTION:
                return new CommandNotFoundExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case USERNOTFOUNDEXCEPTION:
                return new UserNotFoundExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration);
            default:
                return new ErrorExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration);
        }
    }

    public AbstractEmbedGenerator getHelp(String command) {
        switch (command) {
            case "addcommand":
                return new CreateCommandHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case "helpcommand":
                return new HelpCommandHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case "modifycommand":
                return new ModifyCommandHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case "profile":
                return new ProfileCommandHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case "removecommand":
                return new RemoveCommandHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case "setcolor":
                return new SetColorCommandHelpEmbed(basicCommandDao, discordBotConfiguration);
            default:
                return new StaticHelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
        }
    }
}
