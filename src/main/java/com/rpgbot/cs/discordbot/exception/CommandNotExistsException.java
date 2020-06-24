package com.rpgbot.cs.discordbot.exception;

public class CommandNotExistsException extends RuntimeException {
	public CommandNotExistsException(String command) {
		super("Command " + command + " does not exist.");
	}
}
