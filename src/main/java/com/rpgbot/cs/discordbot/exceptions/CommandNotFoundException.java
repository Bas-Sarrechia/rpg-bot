package com.rpgbot.cs.discordbot.exceptions;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
