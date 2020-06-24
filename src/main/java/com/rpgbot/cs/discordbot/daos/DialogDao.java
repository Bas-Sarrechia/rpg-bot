package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogDao extends JpaRepository<Dialog, Long> {
}
