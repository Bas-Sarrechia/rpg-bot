package com.rpgbot.cs.discordbot.entities;

import lombok.*;

import javax.persistence.*;
import java.awt.*;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Transient
    public static int MAX_CHARACTERS = 5;
    @Id
    private Long id;
    private Authorization authorization;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Character> characters;

}
