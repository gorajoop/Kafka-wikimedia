package joop.kafka.stream.wikimedia.source;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import jakarta.annotation.PostConstruct;
import joop.kafka.stream.wikimedia.handler.WikimediaEventSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class WikimediaEventSource {

    @Value("${wikimedia.recentchange.url: https://stream.wikimedia.org/v2/stream/recentchange}")
    private String wikimediaUrl;

    @Autowired
    private WikimediaEventSourceHandler eventHandler;

    private BackgroundEventSource backgroundEventSource;

    public WikimediaEventSource(WikimediaEventSourceHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @PostConstruct
    public void init() {
        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(wikimediaUrl));
        backgroundEventSource = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder).build();
    }

    public BackgroundEventSource getBackgroundEventSource() {
        return backgroundEventSource;
    }

    public String getEventSourceUrl() {
        return this.wikimediaUrl;
    }
}
