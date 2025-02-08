package joop.kafka.stream.wikimedia;

import joop.kafka.stream.wikimedia.service.WikimediaEventFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

    private final WikimediaEventFetcher wikimediaEventFetcher;

    public AppRunner(WikimediaEventFetcher wikimediaEventFetcher) {
        this.wikimediaEventFetcher = wikimediaEventFetcher;
    }

    @Override
    public void run(String... args) throws Exception {
        // Fetch and process Wikimedia events
        wikimediaEventFetcher.fetchEvents()
                .subscribe(event -> {
                    log.info("Received WikiEvent:{} ",event);
                    // Process the event (e.g., send to Kafka, save to database, etc.)
                });
    }
}
