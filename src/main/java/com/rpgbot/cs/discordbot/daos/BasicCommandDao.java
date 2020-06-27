package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.BasicCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicCommandDao extends JpaRepository<BasicCommand, Long> {
    Optional<BasicCommand> findByCommandCommandText(String commandText);
}
