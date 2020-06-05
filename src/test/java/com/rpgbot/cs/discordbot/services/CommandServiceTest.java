package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.daos.BasicCommandDao;
import com.rpgbot.cs.discordbot.daos.CommandDao;
import com.rpgbot.cs.discordbot.daos.EmbedCommandDao;
import com.rpgbot.cs.discordbot.entities.BasicCommand;
import com.rpgbot.cs.discordbot.entities.Command;
import com.rpgbot.cs.discordbot.entities.EmbedCommand;
import com.rpgbot.cs.discordbot.exceptions.CommandNameTakenException;
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

import java.awt.*;
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
    private final String description = "MyDescription";
    private final Color color = Color.RED;

    @Mock
    private BotService mockBotService;
    @Mock
    private BasicCommandDao mockBasicCommandDao;
    @Mock
    private EmbedCommandDao mockEmbedCommandDao;
    @Mock
    private CommandDao mockCommandDao;
    @Mock
    private Command mockCommand;
    @Mock
    private BasicCommand mockBasicCommand;
    @Mock
    private EmbedCommand mockEmbedCommand;

    @InjectMocks
    private CommandService commandService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(
                mockBasicCommandDao,
                mockEmbedCommandDao,
                mockCommand,
                mockBotService
        );
    }

    // registerEmbedCommand tests
	@Test
	public void registerEmbedCommandShouldThrowCommandNameTakenException() {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText(command);

    	CommandNameTakenException commandNameTakenException = assertThrows(
    			CommandNameTakenException.class,
			    () -> { commandService.registerEmbedCommand(command, response); }
	    );

    	assertEquals(commandNameTakenException.getMessage(), command);
	}

	//TODO sara once iffy writes registerBasicCommandShouldSave() read how he did it and do this
	@Test
	public void registerEmbedCommandShouldSave() {

	}

    // registerBasicCommand tests
	@Test
	public void registerBasicCommandShouldThrowCommandNameTakenException() {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText(command);

    	CommandNameTakenException commandNameTakenException = assertThrows(
    			CommandNameTakenException.class,
			    () -> { commandService.registerBasicCommand(command, response); }
	    );

    	assertEquals(commandNameTakenException.getMessage(), command);

	}

	//TODO iffy can u write this test it makes my head hurt trying to figure out
	@Test
	public void registerBasicCommandShouldSave() {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText(command);

	}

	// setEmbedDescriptionCommand tests
	@Test
	public void setEmbedDescriptionCommandShouldThrowCommandNotFoundException() {
		Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

		doReturn(mockEmbedCommandOptional)
				.when(mockEmbedCommandDao)
				.findByCommandCommandText(notCommand);

		CommandNotFoundException commandNotFoundException = assertThrows(
				CommandNotFoundException.class,
				() -> { commandService.setEmbedDescriptionCommand(command, response); }
		);

		assertEquals(commandNotFoundException.getMessage(), command);
	}

	@Test
	public void setEmbedDescriptionCommandShouldSetDescription() throws CommandNotFoundException {
		Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

		doReturn(mockEmbedCommandOptional)
				.when(mockEmbedCommandDao)
				.findByCommandCommandText(command);

		commandService.setEmbedDescriptionCommand(command, response);

		verify(mockEmbedCommand, times(1))
				.setDescription(response);
	}

	@Test
	public void setEmbedDescriptionCommandShouldSave() throws CommandNotFoundException {
		Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

		doReturn(mockEmbedCommandOptional)
				.when(mockEmbedCommandDao)
				.findByCommandCommandText(command);

		commandService.setEmbedDescriptionCommand(command, response);

		verify(mockEmbedCommandDao, times(1))
				.save(mockEmbedCommand);

	}

	// lookupEmbedCommand tests
	@Test
	public void lookupEmbedCommandShouldThrowCommandNotFoundException() {
		Optional<BasicCommand> basicCommandOptional = Optional.of(mockBasicCommand);

		doReturn(basicCommandOptional)
				.when(mockBasicCommandDao)
				.findByCommandCommandText(notCommand);

		CommandNotFoundException commandNotFoundException = assertThrows(
				CommandNotFoundException.class,
				() -> { commandService.lookupStaticCommand(command); }
		);
	}

	@Test
	public void lookupEmbedCommandShouldReturnCommand() throws CommandNotFoundException {
		Optional<BasicCommand> mockBasicCommandOptional = Optional.of(mockBasicCommand);

		doReturn(mockBasicCommandOptional)
				.when(mockBasicCommandDao)
				.findByCommandCommandText(command);

		assertEquals(mockBasicCommand, commandService.lookupStaticCommand(command));
	}

	// setEmbedColorCommand tests
	@Test
	public void setEmbedColorCommandShouldThrowCommandNotFoundException() {
		Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

		doReturn(mockEmbedCommandOptional)
				.when(mockEmbedCommandDao)
				.findByCommandCommandText(notCommand);

		CommandNotFoundException commandNotFoundException = assertThrows(
				CommandNotFoundException.class,
				() -> { commandService.setEmbedColorCommand(command, Color.RED); }
		);

		assertEquals(commandNotFoundException.getMessage(), command);
	}

	@Test
	public void setEmbedColorCommandShouldSetColor() throws CommandNotFoundException {
		Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

		doReturn(mockEmbedCommandOptional)
				.when(mockEmbedCommandDao)
				.findByCommandCommandText(command);

		commandService.setEmbedColorCommand(command, color);

		verify(mockEmbedCommand, times(1))
				.setColor(color);
	}

	@Test
	public void setEmbedColorCommandShouldSave() throws CommandNotFoundException {
		Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

		doReturn(mockEmbedCommandOptional)
				.when(mockEmbedCommandDao)
				.findByCommandCommandText(command);

		commandService.setEmbedColorCommand(command, color);

		verify(mockEmbedCommandDao, times(1))
				.save(mockEmbedCommand);

	}

	// setEmbedTitleCommand tests
	@Test
	public void setEmbedTitleCommandShouldThrowCommandNotFoundException() {
    	Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

    	doReturn(mockEmbedCommandOptional)
			    .when(mockEmbedCommandDao)
			    .findByCommandCommandText(notCommand);

    	CommandNotFoundException commandNotFoundException = assertThrows(
    			CommandNotFoundException.class,
			    () -> { commandService.setEmbedTitleCommand(command, response); }
	    );

    	assertEquals(commandNotFoundException.getMessage(), command);
	}

	@Test
	public void setEmbedTitleCommandShouldSetTitle() throws CommandNotFoundException {
    	Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

    	doReturn(mockEmbedCommandOptional)
			    .when(mockEmbedCommandDao)
			    .findByCommandCommandText(command);

    	commandService.setEmbedTitleCommand(command, response);

    	verify(mockEmbedCommand, times(1))
			    .setTitle(response);
	}

	@Test
	public void setEmbedTitleCommandShouldSave() throws CommandNotFoundException {
    	Optional<EmbedCommand> mockEmbedCommandOptional = Optional.of(mockEmbedCommand);

    	doReturn(mockEmbedCommandOptional)
			    .when(mockEmbedCommandDao)
			    .findByCommandCommandText(command);

    	commandService.setEmbedTitleCommand(command, response);

    	verify(mockEmbedCommandDao, times(1))
			    .save(mockEmbedCommand);

	}

	// renameCommand tests
	@Test
	public void renameCommandShouldThrowCommandNameTakenException() {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText(notCommand);

		CommandNameTakenException commandNameTakenException = assertThrows(
				CommandNameTakenException.class,
				() -> { commandService.renameCommand(command, notCommand); }
		);

		assertEquals(commandNameTakenException.getMessage(), notCommand);

	}

	@Test
	public void renameCommandShouldThrowCommandNotFoundException() throws CommandNotFoundException, CommandNameTakenException {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText("nonexistent");

    	CommandNotFoundException commandNotFoundException = assertThrows(
    			CommandNotFoundException.class,
			    () -> { commandService.renameCommand(command, notCommand); }
	    );

    	assertEquals(commandNotFoundException.getMessage(), command);
	}

	@Test
	public void renameCommandShouldSetCommandText() throws CommandNotFoundException, CommandNameTakenException {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText(command);

    	commandService.renameCommand(command, notCommand);

    	verify(mockCommand, times(1))
			    .setCommandText(notCommand);

	}

	@Test
	public void renameCommandShouldSaveCommand() throws CommandNotFoundException, CommandNameTakenException {
    	Optional<Command> mockCommandOptional = Optional.of(mockCommand);

    	doReturn(mockCommandOptional)
			    .when(mockCommandDao)
			    .findByCommandText(command);

    	commandService.renameCommand(command, notCommand);

    	verify(mockCommandDao, times(1))
			    .save(mockCommand);

	}

    // setStaticDescriptionCommand tests
	@Test
	public void setStaticDescriptionCommandShouldThrowCommandNotFoundException()  {
		Optional<Command> mockCommandOptional = Optional.of(mockCommand);

		doReturn(mockCommandOptional)
				.when(mockCommandDao)
				.findByCommandText(notCommand);

		CommandNotFoundException commandNotFoundException = assertThrows(
				CommandNotFoundException.class,
				() -> { commandService.setStaticDescriptionCommand(command, "random description"); }
		);

		assertEquals(commandNotFoundException.getMessage(), command);
	}

	@Test
	public void setStaticDescriptionCommandShouldSetDescription() throws CommandNotFoundException {
		Optional<Command> mockCommandOptional = Optional.of(mockCommand);

		doReturn(mockCommandOptional)
				.when(mockCommandDao)
				.findByCommandText(command);

		commandService.setStaticDescriptionCommand(command, description);

		verify(mockCommand, times(1))
				.setDescription(description);

	}

	@Test
	public void setDescriptionCommandShouldSaveCommand() throws CommandNotFoundException {
		Optional<Command> mockCommandOptional = Optional.of(mockCommand);

		doReturn(mockCommandOptional)
				.when(mockCommandDao)
				.findByCommandText(command);

		commandService.setStaticDescriptionCommand(command, description);

		verify(mockCommandDao, times(1))
				.save(mockCommand);
	}

    // lookupStaticCommand tests
    @Test
    public void lookupStaticCommandShouldThrowCommandNotFoundException() {
        Optional<BasicCommand> basicCommandOptional = Optional.of(mockBasicCommand);

        doReturn(basicCommandOptional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(notCommand);

        CommandNotFoundException commandNotFoundException = assertThrows(
                CommandNotFoundException.class,
                () -> { commandService.lookupStaticCommand(command); }
        );
    }

    @Test
    public void lookupStaticCommandShouldReturnCommand() throws CommandNotFoundException {
	    Optional<BasicCommand> mockBasicCommandOptional = Optional.of(mockBasicCommand);

	    doReturn(mockBasicCommandOptional)
			    .when(mockBasicCommandDao)
			    .findByCommandCommandText(command);

	    assertEquals(mockBasicCommand, commandService.lookupStaticCommand(command));
    }

    // removeStaticCommand tests
    @Test
    public void removeStaticCommandShouldThrowCommandNotFoundException() {
        Optional<Command> commandOptional = Optional.of(mockCommand);

        doReturn(commandOptional)
                .when(mockCommandDao)
                .findByCommandText(notCommand);

        CommandNotFoundException commandNotFoundException = assertThrows(
                CommandNotFoundException.class,
                () -> { commandService.removeStaticCommand(command); }
        );

        assertEquals(commandNotFoundException.getMessage(), command);

    }

    @Test
    public void removeStaticCommandShouldDeleteCommand() throws CommandNotFoundException {
        Optional<Command> commandOptional = Optional.of(mockCommand);

        doReturn(commandOptional)
                .when(mockCommandDao)
                .findByCommandText(command);

        commandService.removeStaticCommand(command);

        verify(mockCommandDao, times(1))
                .delete(mockCommand);
    }

    // modifyStaticCommand tests
    @Test
    public void modifyStaticCommandShouldThrowCommandNotFoundException() {
        Optional<BasicCommand> optional = Optional.of(mockBasicCommand);

        doReturn(optional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(notCommand);

        CommandNotFoundException commandNotFoundException = assertThrows(
                CommandNotFoundException.class,
                () -> { commandService.modifyStaticCommand(command, response); }
        );

        assertEquals(commandNotFoundException.getMessage(), command);
    }

    @Test
    public void modifyStaticCommandShouldSetCommandResponse() throws CommandNotFoundException {
        BasicCommand mockBasicCommand = mock(BasicCommand.class);

        Optional<BasicCommand> mockBasicCommandOptional = Optional.of(mockBasicCommand);

        doReturn(mockBasicCommandOptional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(command);

        commandService.modifyStaticCommand(command, response);

        verify(mockBasicCommand , times(1))
                .setResponse(response);
    }

    @Test
    public void modifyStaticCommandShouldSaveCommand() throws CommandNotFoundException {
        Optional<BasicCommand> mockBasicCommandOptional = Optional.of(mockBasicCommand);

        doReturn(mockBasicCommandOptional)
                .when(mockBasicCommandDao)
                .findByCommandCommandText(command);

        commandService.modifyStaticCommand(command, response);

        verify(mockBasicCommandDao , times(1))
                .save(mockBasicCommand);
    }
}
