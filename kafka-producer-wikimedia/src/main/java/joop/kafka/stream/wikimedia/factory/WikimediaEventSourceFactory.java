package joop.kafka.stream.wikimedia.factory;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import joop.kafka.stream.wikimedia.handler.WikimediaEventSourceHandler;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class WikimediaEventSourceFactory {

    public BackgroundEventSource createBackgroundEventSource(String wikimediaUrl, WikimediaEventSourceHandler eventHandler) {
        EventSource.Builder builder = new EventSource.Builder(URI.create(wikimediaUrl));
        return new BackgroundEventSource.Builder(eventHandler, builder).build();
    }
}
