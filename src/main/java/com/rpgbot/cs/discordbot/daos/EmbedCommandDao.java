package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.EmbedCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmbedCommandDao extends JpaRepository<EmbedCommand, Long> {

	Optional<EmbedCommand> findByCommandCommandText(String command);
}
