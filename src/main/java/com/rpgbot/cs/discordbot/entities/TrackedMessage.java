package com.rpgbot.cs.discordbot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackedMessage {
    @Id
    private Long id;
    @ManyToOne
    private User calledBy;
}
