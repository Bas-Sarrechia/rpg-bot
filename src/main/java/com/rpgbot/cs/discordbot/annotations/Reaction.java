package com.rpgbot.cs.discordbot.annotations;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(0)
public @interface Reaction {
    boolean tracked() default false;
}
