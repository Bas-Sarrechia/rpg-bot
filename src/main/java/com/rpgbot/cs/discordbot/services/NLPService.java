package com.rpgbot.cs.discordbot.services;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@Service
public class NLPService {
    private final StanfordCoreNLP pipeline;
    private final BotService botService;

    public NLPService(BotService botService) {
        this.botService = botService;

        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }

    /*
    @PostConstruct
    public void listen() {
        botService.getDiscordApi().addMessageCreateListener(messageCreateEvent -> {
            boolean isBot = messageCreateEvent.getMessageAuthor().asUser().map(User::isBot).orElse(false);
            if (!isBot) {
                Annotation annotation = pipeline.process(messageCreateEvent.getMessageContent());
                List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
                for (CoreMap sentence : sentences) {
                    String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                    System.out.println(sentiment);
                    if (!sentiment.equals("Neutral")) {
                        messageCreateEvent.getChannel().sendMessage("Aww you sound " + sentiment);
                    }
                }
            }
        });
    }
     */
}
