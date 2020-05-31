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
    @Column(unique = true)
    private String commandText;
    @Enumerated(EnumType.STRING)
    private CommandType commandType;
    @Enumerated(EnumType.STRING)
    private Authorization requiredAuthorization;
}
