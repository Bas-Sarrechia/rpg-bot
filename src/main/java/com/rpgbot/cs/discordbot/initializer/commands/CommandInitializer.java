package com.rpgbot.cs.discordbot.initializer.commands;

import com.rpgbot.cs.discordbot.entities.CommandType;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;

@Component
public class CommandInitializer {
    @Getter
    private final EnumMap<CommandType, ICommandInitializer> handlers = new EnumMap<>(CommandType.class);
    private final List<ICommandInitializer> commandInitializes;

    public CommandInitializer(List<ICommandInitializer> commandInitializes) {
        this.commandInitializes = commandInitializes;
    }

    @PostConstruct
    private void init() {
        commandInitializes.parallelStream()
                .forEach(initializer -> this.handlers.put(initializer.getCommandType(), initializer));
    }
}
