package com.rpgbot.cs.discordbot.annotations;

import com.rpgbot.cs.discordbot.events.SelfEmbedReactionEvent;
import com.rpgbot.cs.discordbot.messagehandlers.DiscordMessage;
import com.rpgbot.cs.discordbot.services.DialogService;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

@Component
public class ReactionListener implements BeanPostProcessor {
    private final AbstractApplicationContext context;
    private final DialogService dialogService;

    public ReactionListener(AbstractApplicationContext context, DialogService dialogService) {
        this.context = context;
        this.dialogService = dialogService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(Reaction.class)) {
                Reaction annotation = method.getAnnotation(Reaction.class);
                context.addApplicationListener(createApplicationListener(method, bean, annotation));
            }
        }
        return bean;
    }

    private ApplicationListener<SelfEmbedReactionEvent> createApplicationListener(Method method, Object bean, Reaction command) {
        return (SelfEmbedReactionEvent event) -> {
            try {
                DiscordMessage propagatedMessage = ((DiscordMessage) method.invoke(bean, event));
                CompletableFuture<Message> messageCompletableFuture = null;
                if (propagatedMessage != null) {
                    propagatedMessage.getBody();
                    if (propagatedMessage.getBody() instanceof EmbedBuilder) {
                        messageCompletableFuture = event.getTarget().sendMessage((EmbedBuilder) propagatedMessage.getBody());
                    } else if (propagatedMessage.getBody() instanceof String) {
                        messageCompletableFuture = event.getTarget().sendMessage((String) propagatedMessage.getBody());
                    }
                }

                if(messageCompletableFuture != null) {
                    messageCompletableFuture.thenAcceptAsync(message -> {
                        message.addReactions(propagatedMessage.getEmojis());
                        dialogService.track(message.getId(), event.getUser(), propagatedMessage.getTrackedDialog());
                    });
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

}
