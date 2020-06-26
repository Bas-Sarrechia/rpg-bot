package com.rpgbot.cs.discordbot.services;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NLPService {
            /*
    private final StanfordCoreNLP pipeline;
    private final BotService botService;

    public NLPService(BotService botService) {
        this.botService = botService;

        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }

    @EventListener
    public void listen(DefaultMessageEvent defaultMessageEvent) {

        Annotation annotation = pipeline.process(defaultMessageEvent.getMessage());
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            System.out.println(sentiment);
            if (!sentiment.equals("Neutral")) {
                defaultMessageEvent.getTarget().sendMessage("Aww you sound " + sentiment);
            }
        }
    }
             */
}
