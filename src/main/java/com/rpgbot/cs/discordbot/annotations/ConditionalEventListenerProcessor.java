package com.rpgbot.cs.discordbot.annotations;

import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.messagehandlers.DiscordMessage;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ConditionalEventListenerProcessor implements BeanPostProcessor {
    private final AbstractApplicationContext context;

    public ConditionalEventListenerProcessor(AbstractApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command annotation = method.getAnnotation(Command.class);
                context.addApplicationListener(createApplicationListener(method, bean, annotation.alias()));
            }
        }
        return bean;
    }

    private ApplicationListener<CommandMessageEvent> createApplicationListener(Method method, Object bean, String command) {
        return (CommandMessageEvent event) -> {
            if (command.equals("") || event.getCommand().toLowerCase().equals(command.toLowerCase())) {
                try {
                    Object value = method.invoke(bean, event);
                    if (value instanceof DiscordMessage) {
                        Object body = ((DiscordMessage) value).getBody();
                        if (body instanceof String) {
                            event.getTarget().sendMessage((String) body);
                        } else if (body instanceof EmbedBuilder) {
                            event.getTarget().sendMessage((EmbedBuilder) body);
                        }
                    } else if (value instanceof String) {
                        event.getTarget().sendMessage((String)value);
                    } else if (value instanceof EmbedBuilder) {
                        event.getTarget().sendMessage((EmbedBuilder)value);
                    } else if(value != null) {
                        throw new IllegalArgumentException("return type must be of DiscordMessage");
                    }
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                }
            }
        };
    }
}