package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.TrackedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackedMessageDao extends JpaRepository<TrackedMessage, Long> {
}
