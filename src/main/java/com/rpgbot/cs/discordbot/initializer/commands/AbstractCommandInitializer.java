package com.rpgbot.cs.discordbot.initializer.commands;

import com.rpgbot.cs.discordbot.entities.CommandType;
import com.rpgbot.cs.discordbot.services.CommandService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractCommandInitializer {
    private final CommandType commandType;
    private final CommandService commandService;
    protected AbstractCommandInitializer(CommandType commandType, CommandService commandService) {
        this.commandType = commandType;
        this.commandService = commandService;
    }
}
