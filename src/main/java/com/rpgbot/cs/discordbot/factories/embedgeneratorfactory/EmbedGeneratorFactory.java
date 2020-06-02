package com.rpgbot.cs.discordbot.factories.embedgeneratorfactory;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.exceptions.ExceptionType;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.CommandNotFoundExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.ErrorExceptionEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.HelpEmbedGenerator;
import com.rpgbot.cs.discordbot.factories.embedgeneratorfactory.embedgenerators.UserNotFoundExceptionEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmbedGeneratorFactory {
    private final BasicCommandDao basicCommandDao;
    private final DiscordBotConfiguration discordBotConfiguration;

    public AbstractEmbedGenerator get(EmbedType embedType) {
        switch (embedType) {
            case HELP:
                return new HelpEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case COMMANDNOTFOUNDEXCEPTION:
                return new CommandNotFoundExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration);
            case USERNOTFOUNDEXCEPTION:
                return new UserNotFoundExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration);
            default:
                return new ErrorExceptionEmbedGenerator(basicCommandDao, discordBotConfiguration);
        }
    }
}
