package com.rpgbot.cs.discordbot.router;

import com.rpgbot.cs.discordbot.annotations.Command;
import com.rpgbot.cs.discordbot.entities.Character;
import com.rpgbot.cs.discordbot.entities.User;
import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.exception.UserExistsException;
import com.rpgbot.cs.discordbot.exception.UserNotExistsException;
import com.rpgbot.cs.discordbot.services.UserService;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProfileRouter {

    private final UserService userService;

    public ProfileRouter(UserService userService) {
        this.userService = userService;
    }

    @Command(alias = "register")
    public void register(final CommandMessageEvent commandMessageEvent) {
        try {
            userService.register(commandMessageEvent.getUser());
            commandMessageEvent.getTarget().sendMessage("Registered!");
        } catch (UserExistsException userExistsException) {
            commandMessageEvent.sendError(userExistsException.getMessage());
        }
    }

    @Command(alias = "profile")
    public void getProfile(final CommandMessageEvent commandMessageEvent) {
        try {
            User user = this.userService.findUserById(commandMessageEvent.getUser());
            EmbedBuilder embedMessage = new EmbedBuilder()
                    .setAuthor(commandMessageEvent.getOrigin().getMessageAuthor())
                    .setTitle("Profile");
            Set<Character> characters = user.getCharacters();
            characters.forEach(character -> {
                embedMessage.addField(character.getCharacterName(), " TODO "); //TODO add character details (glod - lvl - profession - ...)
            });
            for (int i = characters.size(); i < User.MAX_CHARACTERS; i++) {
                embedMessage.addField(String.valueOf(i+1), "Open slot");
            }
            commandMessageEvent.getTarget().sendMessage(embedMessage);
        } catch (UserNotExistsException exception) {
            commandMessageEvent.sendError("Profile does not exist. Use the register command");
        }
    }
}
