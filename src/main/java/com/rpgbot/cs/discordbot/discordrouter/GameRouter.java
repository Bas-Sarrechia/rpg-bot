package com.rpgbot.cs.discordbot.discordrouter;

import com.rpgbot.cs.discordbot.entities.DiscordUser;
import com.rpgbot.cs.discordbot.services.BotService;
import com.rpgbot.cs.discordbot.services.UserService;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Optional;

@Controller
public class GameRouter {
    private final BotService botService;
    private final UserService userService;

    public GameRouter(BotService botService, UserService userService) {
        this.botService = botService;
        this.userService = userService;
    }

    @PostConstruct
    public void register() {
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith("!register")) {
                Optional<User> user = messageCreateEvent.getMessageAuthor().asUser();
                String strippedMessage = messageCreateEvent.getMessageContent().substring("!register".length()).stripLeading();

                String[] data = strippedMessage.split(" ");
                if (user.isPresent() && data.length > 1 && data.length <= 3) {
                    DiscordUser discordUser = DiscordUser.builder()
                            .id(user.get().getId())
                            .preferredColor(Color.getColor(data[0]))
                            .nickname(data[1])
                            .build();
                    if (data.length == 3) {
                        discordUser.setImage(data[2]);
                    }
                    this.userService.register(discordUser);
                    messageCreateEvent.getChannel().sendMessage(discordUser.toString());
                } else {
                    messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("How to use this command", "!register <prefered color> <nickname>\n" +
                                    "!register <prefered color> <nickname> <image>")
                            .setFooter("registers you to the game"));
                }
            }
        });
    }

    @PostConstruct
    public void profile() {
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith("!profile")) {
                long userId = messageCreateEvent.getMessageAuthor().asUser()
                        .map(DiscordEntity::getId).orElse(0L);
                DiscordUser discordUser = userService.findUserById(userId);
                if (discordUser != null) {
                    messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                            .setColor(discordUser.getPreferredColor())
                            .setTitle(discordUser.getNickname())
                            .setImage(discordUser.getImage()));
                } else {
                    messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("Not registered", "Register first using the !register command"));
                }
            }
        });
    }
}
