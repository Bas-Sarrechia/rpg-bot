package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.configuration.DiscordBotConfiguration;
import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.daos.CommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.entities.Command;
import com.rpgbot.cs.discordbot.exceptions.CommandNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CommandServiceTest {

    private final String command = "MyCommand";
    private final String notCommand = "NotMyCommand";
    private final String response = "MyResponse";

    @Mock
    private BotService mockBotService;
    @Mock
    private BasicCommandDao mockBasicCommandDao;
    @Mock
    private CommandDao mockCommandDao;
    @Mock
    private DiscordBotConfiguration mockDiscordBotConfiguration;
    @Mock
    private Command mockCommand;
    @Mock
    private BasicCommand mockBasicCommand;

    @InjectMocks
    private CommandService commandService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(
                mockBasicCommandDao,
                mockCommand,
                mockBotService
        );
    }

    // lookupCommandTests
    @Test
    public void lookupCommandShouldThrowCommandNotFoundException() {
        Optional<BasicCommand> basicCommandOptional = Optional.of(mockBasicCommand);

        doReturn(basicCommandOptional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(notCommand);

        CommandNotFoundException commandNotFoundException = assertThrows(
                CommandNotFoundException.class,
                () -> { commandService.lookupCommand(command); }
        );
    }

    @Test
    public void lookupCommandShouldReturnBasicCommand() {
        
    }

    // removeCommandTests
    @Test
    public void removeCommandShouldThrowCommandNotFoundException() {
        Optional<Command> commandOptional = Optional.of(mockCommand);

        doReturn(commandOptional)
                .when(mockCommandDao)
                .findByCommandText(notCommand);

        CommandNotFoundException commandNotFoundException = assertThrows(
                CommandNotFoundException.class,
                () -> { commandService.removeCommand(command); }
        );

        assertEquals(commandNotFoundException.getMessage(), command);

    }

    @Test
    public void removeCommandShouldDeleteCommand() throws CommandNotFoundException {
        Optional<Command> commandOptional = Optional.of(mockCommand);

        doReturn(commandOptional)
                .when(mockCommandDao)
                .findByCommandText(command);

        commandService.removeCommand(command);

        verify(mockCommandDao, times(1))
                .delete(mockCommand);
    }

    // modifyCommandTests
    @Test
    public void modifyCommandShouldThrowCommandNotFoundException() {
        Optional<BasicCommand> optional = Optional.of(mockBasicCommand);

        doReturn(optional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(notCommand);

        CommandService commandService = new CommandService(mockBotService, mockBasicCommandDao, mockCommandDao, mockDiscordBotConfiguration);

        CommandNotFoundException commandNotFoundException = assertThrows(
                CommandNotFoundException.class,
                () -> { commandService.modifyCommand(command, response); }
        );

        assertEquals(commandNotFoundException.getMessage(), command);
    }

    @Test
    public void modifyCommandShouldSetCommandResponse() throws CommandNotFoundException {
        BasicCommand mockBasicCommand = mock(BasicCommand.class);

        Optional<BasicCommand> optional = Optional.of(mockBasicCommand);

        doReturn(optional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(command);

        CommandService commandService = new CommandService(mockBotService, mockBasicCommandDao, mockCommandDao, mockDiscordBotConfiguration);

        commandService.modifyCommand(command, response);

        verify(mockBasicCommand , times(1))
                .setResponse(response);
    }

    @Test
    public void modifyCommandShouldSaveCommand() throws CommandNotFoundException {
        Optional<BasicCommand> optional = Optional.of(mockBasicCommand);

        doReturn(optional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(command);

        CommandService commandService = new CommandService(mockBotService, mockBasicCommandDao, mockCommandDao, mockDiscordBotConfiguration);

        commandService.modifyCommand(command, response);

        verify(mockBasicCommandDao , times(1))
                .save(mockBasicCommand);
    }
}
