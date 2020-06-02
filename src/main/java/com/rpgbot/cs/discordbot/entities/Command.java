package com.rpgbot.cs.discordbot.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commandText;
    private String description;
    private String usage;
    @Enumerated(EnumType.STRING)
    private CommandType commandType;
    @Enumerated(EnumType.STRING)
    private Authorization requiredAuthorization;
}
