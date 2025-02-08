package joop.kafka.stream.wikimedia.service;

import joop.kafka.stream.wikimedia.source.WikimediaEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WikimediaProducer {

    private static Logger log = LoggerFactory.getLogger(WikimediaProducer.class);
    private final WikimediaEventSource wikimediaEventSource;

    @Autowired
    public WikimediaProducer(WikimediaEventSource wikimediaEventSource) {this.wikimediaEventSource = wikimediaEventSource;}

    public void sendMessage() {
        this.wikimediaEventSource.getBackgroundEventSource().start();
        log.info("Start reading message from url stream {}",
                 wikimediaEventSource.getEventSourceUrl());
        // Simulate a delay (for demonstration purposes in  test context)
        // TimeUnit.MINUTES.sleep(2);
    }
}