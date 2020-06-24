package com.rpgbot.cs.discordbot.exception;

public class DialogNotFoundException extends RuntimeException {
    public DialogNotFoundException(){
        super("Dialog reference was not found.");
    }
}
