package com.rpgbot.cs.discordbot.entities;

import lombok.*;

import javax.persistence.*;
import java.awt.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DiscordUser {
    @Id
    private Long id;
    private Color preferredColor;
    private String nickname;
    private String image;
}
