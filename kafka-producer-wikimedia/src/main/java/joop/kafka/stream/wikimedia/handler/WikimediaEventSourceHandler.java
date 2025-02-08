package joop.kafka.stream.wikimedia.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import joop.kafka.stream.wikimedia.model.RecentChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WikimediaEventSourceHandler implements BackgroundEventHandler {

    private static final Logger log = LoggerFactory.getLogger(
            WikimediaEventSourceHandler.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.wikimedia}")
    private String topic;

    @Autowired
    public WikimediaEventSourceHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onOpen() throws Exception {}

    @Override
    public void onClosed() throws Exception {}

    @Override
    public void onMessage(String s, MessageEvent messageEvent)
            throws Exception {
        log.info("Événement reçu : => {}", messageEvent.getData());
        log.info("Événement reçu : => {}", parseMessage(messageEvent.getData()));
        kafkaTemplate.send(topic,messageEvent.getData());
    }

    @Override
    public void onComment(String s) throws Exception {}

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    private RecentChangeEvent parseMessage(String eventJson){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RecentChangeEvent result = objectMapper.readValue(eventJson,
                                                              RecentChangeEvent.class);
            log.info("convert to RecentChangeEvent {}",result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Wikimedia event", e);
        }
    }
}

