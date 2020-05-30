package com.rpgbot.cs.discordbot.initializer.commands;

import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.entities.CommandType;
import com.rpgbot.cs.discordbot.services.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BasicCommandInitializer extends AbstractCommandInitializer implements ICommandInitializer {
    private final BasicCommandDao basicCommandDao;

    @Autowired
    public BasicCommandInitializer(BasicCommandDao basicCommandDao, CommandService commandService) {
        super(CommandType.BASIC, commandService);
        this.basicCommandDao = basicCommandDao;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        basicCommandDao
                .findByCommandCommandType(super.getCommandType())
                .forEach(basicCommand -> this.getCommandService().addBasicCommandToBot(basicCommand));
    }
}