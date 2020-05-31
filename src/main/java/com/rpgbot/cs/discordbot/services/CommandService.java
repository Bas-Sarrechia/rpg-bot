package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.daos.CommandDao;
import com.rpgbot.cs.discordbot.entities.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Basic;
import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class CommandService {
    private final BotService botService;
    private final BasicCommandDao basicCommandDao;
    private final CommandDao commandDao;

    public CommandService(BotService botService, BasicCommandDao basicCommandDao, CommandDao commandDao) {
        this.botService = botService;
        this.basicCommandDao = basicCommandDao;
        this.commandDao = commandDao;
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

//    public void addBasicCommandToBot(BasicCommand basicCommand) {
//        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
//            if (messageCreateEvent.getMessageContent().toLowerCase().startsWith(basicCommand.getCommand().getCommandText().toLowerCase())) {
//                // message             content                              starts with command
//                messageCreateEvent.getChannel().sendMessage(basicCommand.getResponse());
////                messageCreateEvent.getChannel().sendMessage("SARA TESTING SHIT");
//            }
//        });
//    }

    public void registerBasicCommand(String command, String respond) {
        BasicCommand basicCommand = basicCommandDao.save(BasicCommand.builder()
                .response(respond)
                .command(Command.builder()
                        .commandText(command)
                        .requiredAuthorization(Authorization.BASIC)
                        .commandType(CommandType.BASIC)
                        .build())
                .build());

        basicCommandDao.save(basicCommand);

    }

    public BasicCommand lookupCommand(String command) {
        return basicCommandDao.findByCommandCommandText(command).orElse(null);
    }

    public void removeCommand(String commandName) {
        Optional<Command> command = commandDao.findByCommandText(commandName);
        if (command.isPresent()) {
            commandDao.delete(command.get());
        }
    }

    public void modifyCommand(String command, String respond) {
        Optional<BasicCommand> basicCommandOptional = basicCommandDao.findByCommandCommandText(command);
        if (basicCommandOptional.isPresent()) {
            BasicCommand basicCommand = basicCommandOptional.get();
            basicCommand.setResponse(respond);
            basicCommandDao.save(basicCommand);
        }

    }
}
