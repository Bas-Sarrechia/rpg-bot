package com.rpgbot.cs.discordbot.services;

import com.rpgbot.cs.discordbot.events.DefaultMessageEvent;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
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
