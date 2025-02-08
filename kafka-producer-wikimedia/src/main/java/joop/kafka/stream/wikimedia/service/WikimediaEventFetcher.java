package joop.kafka.stream.wikimedia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import joop.kafka.stream.wikimedia.model.RecentChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Service
public class WikimediaEventFetcher {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(
            WikimediaEventFetcher.class);


    public WikimediaEventFetcher() {
        this.webClient = WebClient.create("https://stream.wikimedia.org/v2/stream/recentchange");
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Fetches Wikimedia events and converts them to WikiEvent objects.
     *
     * @return A Flux of WikiEvent objects.
     */
    public Flux<RecentChangeEvent> fetchEvents() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(String.class)
                .map(this::parseEvent);
    }

    /**
     * Parses a JSON string into a WikiEvent object.
     *
     * @param eventJson The JSON string representing the event.
     * @return The parsed WikiEvent object.
     */
    private RecentChangeEvent parseEvent(String eventJson) {
        try {
            log.info("eventJson : {}",eventJson);
            return objectMapper.readValue(eventJson, RecentChangeEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Wikimedia event", e);
        }
    }
}
