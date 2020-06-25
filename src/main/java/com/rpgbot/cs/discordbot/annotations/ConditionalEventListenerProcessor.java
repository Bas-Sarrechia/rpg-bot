package com.rpgbot.cs.discordbot.annotations;

import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
import com.rpgbot.cs.discordbot.messagehandlers.DiscordMessage;
import com.rpgbot.cs.discordbot.services.DialogService;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

@Component
public class ConditionalEventListenerProcessor implements BeanPostProcessor {
    private final AbstractApplicationContext context;
    private final DialogService dialogService;

    @Autowired
    public ConditionalEventListenerProcessor(AbstractApplicationContext context, DialogService dialogService) {
        this.context = context;
        this.dialogService = dialogService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command annotation = method.getAnnotation(Command.class);
                context.addApplicationListener(createApplicationListener(method, bean, annotation));
            }
        }
        return bean;
    }

    private ApplicationListener<CommandMessageEvent> createApplicationListener(Method method, Object bean, Command command) {
        return (CommandMessageEvent event) -> {
            if (command.equals("") || event.getCommand().toLowerCase().equals(command.alias().toLowerCase())) {
                try {
                    Object value = method.invoke(bean, event);
                    CompletableFuture<Message> messageCompletableFuture = null;
                    if (value instanceof DiscordMessage) {
                        Object body = ((DiscordMessage) value).getBody();
                        if (body instanceof String) {
                            messageCompletableFuture = event.getTarget().sendMessage((String) body);
                        } else if (body instanceof EmbedBuilder) {
                            messageCompletableFuture = event.getTarget().sendMessage((EmbedBuilder) body);
                        }
                    } else if (value != null) {
                        throw new IllegalArgumentException("return type must be of DiscordMessage");
                    }

                    if (command.tracked() && messageCompletableFuture != null) {
                        messageCompletableFuture.thenAcceptAsync(message -> {
                            message.addReactions(((DiscordMessage) value).getEmojis());
                            dialogService.track(message.getId(), event.getUser());
                        });
                    }

                } catch (IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                }
            }
        };
    }
}