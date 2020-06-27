package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.events.DefaultMessageEvent;
import com.rpgbot.cs.discordbot.events.SelfEmbedReactionEvent;
import lombok.Getter;
import org.apache.lucene.util.ArrayUtil;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BotService {

    private final DiscordBotConfiguration discordBotConfiguration;
    @Getter
    private DiscordApi discordApi;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public BotService(DiscordBotConfiguration discordBotConfiguration, ApplicationEventPublisher applicationEventPublisher) {
        this.discordBotConfiguration = discordBotConfiguration;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    private void init() {
        this.discordApi = new DiscordApiBuilder()
                .setToken(discordBotConfiguration.getToken())
                .login()
                .join();
        this.createDefaultListener();
    }

    private void createDefaultListener() {
        addMessageListener();
        addReactionListener();
    }

    private void addMessageListener(){
        this.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            String message = messageCreateEvent.getMessageContent();
            if (!messageCreateEvent.getMessageAuthor().asUser().map(User::isBot).orElse(false)) {
                if (message.startsWith(this.discordBotConfiguration.getPrefix()) && message.length() > this.discordBotConfiguration.getPrefix().length()) {
                    String[] commandWithArgs = message
                            .substring(message.indexOf(this.discordBotConfiguration.getPrefix()))
                            .split(" ");

                    applicationEventPublisher.publishEvent(new CommandMessageEvent(this, message, commandWithArgs[0].substring(this.discordBotConfiguration.getPrefix().length()),
                            ArrayUtil.copyOfSubArray(commandWithArgs, 1, commandWithArgs.length),
                            messageCreateEvent.getMessageAuthor().getId(), messageCreateEvent.getChannel(), messageCreateEvent));
                } else {
                    applicationEventPublisher.publishEvent(new DefaultMessageEvent(this, message, messageCreateEvent.getMessageAuthor().getId(), messageCreateEvent.getChannel(), messageCreateEvent));
                }
            }
        });
    }
    private void addReactionListener(){
        this.getDiscordApi().addReactionAddListener(reactionAddEvent -> {
            reactionAddEvent.getMessage().flatMap(message -> message.getAuthor().asUser()).ifPresent(user -> {
                if (user.isYourself() && !reactionAddEvent.getUser().isYourself()) {
                    applicationEventPublisher.publishEvent(new SelfEmbedReactionEvent(this, reactionAddEvent.getUser().getId(), reactionAddEvent.getEmoji().asUnicodeEmoji().orElse(""), reactionAddEvent.getChannel(), reactionAddEvent));
                }
            });
        });
    }
}
