package com.rpgbot.cs.discordbot.annotations;

import com.rpgbot.cs.discordbot.events.CommandMessageEvent;
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
        for (Method method: bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command annotation = method.getAnnotation(Command.class);
                context.addApplicationListener(createApplicationListener(method, bean, annotation.alias()));
            }
        }
        return bean;
    }

    private ApplicationListener<CommandMessageEvent> createApplicationListener(Method m, Object bean, String command) {
        return (CommandMessageEvent event) -> {
            if (command.equals("") || event.getCommand().equals(command)) { // Filter here!
                try {
                    m.invoke(bean, event);
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        };
    }
}