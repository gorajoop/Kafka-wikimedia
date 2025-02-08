package joop.kafka.stream.consumer.service;

import joop.kafka.stream.consumer.entity.WikimediaData;
import joop.kafka.stream.consumer.repository.WikimediaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class KafkaConsumerDBTest {

    private static final Logger log = LoggerFactory.getLogger(
            KafkaConsumerDBTest.class);

    @Mock
    private WikimediaDataRepository wikimediaDataRepository;

    @InjectMocks
    private KafkaConsumerDB kafkaConsumerDB;

    @Test
    void consume() {
        String message = "Message event sent";
        WikimediaData messageEvent = new WikimediaData();
        messageEvent.setId(1L);

        messageEvent.setMessage(message);
        when(wikimediaDataRepository.save(any(WikimediaData.class))).thenReturn(messageEvent);
        kafkaConsumerDB.consume(message);

        verify(wikimediaDataRepository, times(1)).save(any(WikimediaData.class));
        assertThat(message).isSameAs(messageEvent.getMessage());
        log.info("Message Event processed and saved to the database: {}", messageEvent);
    }
}