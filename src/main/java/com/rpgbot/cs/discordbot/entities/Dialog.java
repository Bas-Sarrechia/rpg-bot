package com.rpgbot.cs.discordbot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1500)
    private String text;
    private String linkText;
    @OneToMany(fetch = FetchType.LAZY)
    private Map<String ,Dialog> followUp;
}
