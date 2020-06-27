package com.rpgbot.cs.discordbot.exception;

public class IllegalTrackedException extends RuntimeException {
    public IllegalTrackedException() {
        super("Was not invoked by the user.");
    }
}
