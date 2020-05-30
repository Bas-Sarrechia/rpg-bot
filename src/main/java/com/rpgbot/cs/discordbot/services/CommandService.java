package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Optional;

@Service
public class CommandService {
    private final BotService botService;
    private final BasicCommandDao basicCommandDao;

    public CommandService(BotService botService, BasicCommandDao basicCommandDao) {
        this.botService = botService;
        this.basicCommandDao = basicCommandDao;
    }

    @PostConstruct
    private void addCharacterSelectionMenu(){
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith("!characters")) {

                messageCreateEvent.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.PINK)
                        .setAuthor("Hi")
                        );
            }
        });
    }

    @PostConstruct
    private void register(){
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith("!register")) {
                Optional<User> discordUser = messageCreateEvent.getMessageAuthor().asUser();
                if(discordUser.isPresent()){
                   User user = discordUser.get();
                    DiscordUser.builder()
                            .id(user.getId())
                            .preferredColor(Color.pink)
                            .nickname("")
                            .build();
                }

            }
        });
    }

    public void addBasicCommandToBot(BasicCommand basicCommand) {
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith(basicCommand.getCommand().getCommandText().toLowerCase())) {
                messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
            }
        });
    }

    public void addBasicCommandToBot(String command, String respond) {
        BasicCommand basicCommand = basicCommandDao.save(BasicCommand.builder()
                .response(respond)
                .command(Command.builder()
                        .commandText(command)
                        .requiredAuthorization(Authorization.BASIC)
                        .commandType(CommandType.BASIC)
                        .build())
                .build());

        basicCommandDao.save(basicCommand);

        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith(basicCommand.getCommand().getCommandText().toLowerCase())) {
                messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
            }
        });
    }
}
