package com.rpgbot.cs.discordbot.initializer.commands;

import com.rpgbot.cs.discordbot.entities.CommandType;

public interface ICommandInitializer {
    void initialize();
    CommandType getCommandType();
}
