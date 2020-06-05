package com.rpgbot.cs.discordbot.exceptions;

public class CommandNameTakenException extends Exception {
	public CommandNameTakenException(String command) { super(command); }
}
