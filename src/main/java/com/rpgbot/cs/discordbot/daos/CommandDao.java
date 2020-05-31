package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandDao extends JpaRepository<Command, Long> {
    Optional<Command> findByCommandText(String commandText);
}