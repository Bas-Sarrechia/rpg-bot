package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordUserDao extends JpaRepository<DiscordUser, Long> {
}
