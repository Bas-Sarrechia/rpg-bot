package com.rpgbot.cs.discordbot.exception;

public class CommandExistsException extends RuntimeException {
    public CommandExistsException(String command) {
        super("Command " + command + " already exists.");
    }
}
