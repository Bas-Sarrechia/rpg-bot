package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandDao extends JpaRepository<Command, Long> {
}