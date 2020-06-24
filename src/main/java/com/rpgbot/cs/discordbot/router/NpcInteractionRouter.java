package com.rpgbot.cs.discordbot.router;

import com.rpgbot.cs.discordbot.annotations.Command;
import com.rpgbot.cs.discordbot.entities.Dialog;
import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.messagehandlers.DiscordMessage;
import com.rpgbot.cs.discordbot.services.DialogService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class NpcInteractionRouter {
    private final DialogService dialogService;

    @Autowired
    public NpcInteractionRouter(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @Command(alias = "test")
    public DiscordMessage displayDialog(final CommandMessageEvent commandMessageEvent) {
        Dialog dialog = dialogService.getDialog(0);

        EmbedBuilder embed = new EmbedBuilder().setColor(Color.pink);
        DiscordMessage message = DiscordMessage.embedded(embed);
        StringBuilder displayText = new StringBuilder(dialog.getText()
                .replaceAll("\\\\n+", System.lineSeparator()));

        if (dialog.getFollowUp().size() > 0) {
            displayText.append("\n");
            for (String key : dialog.getFollowUp().keySet()) {
                displayText.append("\n").append(key).append(" : ").append(dialog.getFollowUp().get(key).getLinkText());
            }

            String[] emojis = new String[dialog.getFollowUp().size()];
            dialog.getFollowUp().keySet().toArray(emojis);
            message.setEmojis(emojis);
        }

        embed.setDescription(displayText.toString());
        return message;
    }
}
