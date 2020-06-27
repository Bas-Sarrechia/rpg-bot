package com.rpgbot.cs.discordbot.exception;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException() {
        super("User does not exist.");
    }
}
