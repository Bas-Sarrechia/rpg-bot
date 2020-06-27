package com.rpgbot.cs.discordbot.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User already registered.");
    }
}
