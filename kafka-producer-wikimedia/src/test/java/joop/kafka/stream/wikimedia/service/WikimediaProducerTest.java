package joop.kafka.stream.wikimedia.service;

import com.launchdarkly.eventsource.background.BackgroundEventSource;
import joop.kafka.stream.wikimedia.source.WikimediaEventSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)

public class WikimediaProducerTest {

    private static final Logger log = LoggerFactory.getLogger(WikimediaProducerTest.class);

   @Mock
   BackgroundEventSource backgroundEventSource;

    @Mock
    private WikimediaEventSource wikimediaEventSource;

    @InjectMocks
    private WikimediaProducer wikimediaProducer;

    @Test
    public void testSendMessage() throws InterruptedException {
        log.info("When producer send message wikimediaEventSource start");
        // Arrange
        when(wikimediaEventSource.getBackgroundEventSource()).thenReturn(backgroundEventSource);
        // Act
        wikimediaProducer.sendMessage();
        // Assert
        verify(wikimediaEventSource, times(1)).getBackgroundEventSource();
        verify(wikimediaEventSource.getBackgroundEventSource(), times(1)).start();
    }
}