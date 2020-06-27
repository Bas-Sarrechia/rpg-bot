package com.rpgbot.cs.discordbot.daos;

import com.rpgbot.cs.discordbot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
