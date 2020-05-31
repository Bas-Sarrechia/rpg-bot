package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.exception.CommandException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

@Aspect
public class GlobalExceptionHandler {
    @AfterThrowing(throwing = "exception")
    void test(CommandException exception) {
        exception.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .addField("How to use this command", exception.getBody())
                .setFooter(exception.getDetails()));
    }
}
