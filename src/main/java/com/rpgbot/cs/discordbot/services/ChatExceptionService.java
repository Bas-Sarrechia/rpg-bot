package com.rpgbot.cs.discordbot.services;

import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class ChatExceptionService {

    public EmbedBuilder generateExceptionEmbed(Exception e) {

        return new EmbedBuilder().setTitle("Error!").setDescription(e.getLocalizedMessage()).setColor(Color.RED);

    }
}
