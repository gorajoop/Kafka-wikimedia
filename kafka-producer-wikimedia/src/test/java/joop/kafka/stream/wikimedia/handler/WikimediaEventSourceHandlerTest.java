package joop.kafka.stream.wikimedia.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.launchdarkly.eventsource.MessageEvent;
import joop.kafka.stream.wikimedia.model.RecentChangeEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WikimediaEventSourceHandlerTest {

    private static final Logger log = LoggerFactory.getLogger(
            WikimediaEventSourceHandlerTest.class);
    private static final String TOPIC = "wikimedia.recentchange";
    private static final String GROUP_ID = "test-group";


    @InjectMocks
    private WikimediaEventSourceHandler wikimediaEventSourceHandler;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private Consumer<String, String> consumer;
    private EmbeddedKafkaKraftBroker embeddedKafkaKraftBroker;

    @BeforeEach
    public void setUp() throws IOException {
        // Step 1: Define Kafka broker properties
        Properties kafkaProperties = getKafkaBrokerProperties();

        // Step 2: Initialize Embedded Kafka KRaft Broker
        embeddedKafkaKraftBroker = new EmbeddedKafkaKraftBroker(1, 1, TOPIC);
        embeddedKafkaKraftBroker.brokerProperties((Map) kafkaProperties);
        embeddedKafkaKraftBroker.afterPropertiesSet(); // Start the broker

        // Step 3: Set up KafkaTemplate (Producer)
        Map<String, Object> producerProps = getProducerConfiguration(embeddedKafkaKraftBroker);
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(
                producerProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);

        // Step 4: Initialize the handler with the KafkaTemplate and topic
        wikimediaEventSourceHandler = new WikimediaEventSourceHandler(kafkaTemplate);

        // Step 5: Set up Kafka Consumer
        Map<String, Object> consumerProps = getConsumerConfiguration(embeddedKafkaKraftBroker,
                                                                     GROUP_ID);
        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(TOPIC));

        // Inject the mocked dependencies into the wikimediaEventSourceHandler
        ReflectionTestUtils.setField(wikimediaEventSourceHandler, "topic", TOPIC);

    }

    @AfterEach
    public void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    public void testOnMessage() throws Exception {
        // Arrange
        String recentChangeEventJson;
        try {
            recentChangeEventJson = new String(getClass().getClassLoader().getResourceAsStream(
                    "recentChangeEvent.json").readAllBytes());
            log.info("recentChangeEventJson : {}", recentChangeEventJson);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        MessageEvent messageEvent = new MessageEvent(recentChangeEventJson);

        ObjectMapper objectMapper = new ObjectMapper();
        RecentChangeEvent recentChangeEvent = objectMapper.readValue(recentChangeEventJson,
                                                                     RecentChangeEvent.class);
        //when(wikimediaEventSourceHandler.onMessage(anyString(), any(MessageEvent.class))).then();
        // Act
        wikimediaEventSourceHandler.onMessage("event", messageEvent);
        log.info("MessageEvent sent");
        // Wait for the message to be sent and received
        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer,
                                                                            Duration.ofSeconds(10));

        // Verify that the message was received by the consumer
        assertEquals(1, records.count());
        // Assert
        records.forEach(record -> {
            assertEquals(recentChangeEventJson, record.value());
            assertEquals(TOPIC, record.topic());
            log.info("record retrieve from test topic {} OK",TOPIC);
        });
    }

    private Map<String, Object> getConsumerConfiguration(EmbeddedKafkaKraftBroker embeddedKafkaKraftBroker, String groupId) {
        Map<String, Object> consumerProps = new HashMap<>(
                KafkaTestUtils.consumerProps(groupId, "true", embeddedKafkaKraftBroker));
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return consumerProps;
    }

    private Map<String, Object> getProducerConfiguration(EmbeddedKafkaKraftBroker embeddedKafkaKraftBroker) {
        Map<String, Object> producerProps = new HashMap<>(
                KafkaTestUtils.producerProps(embeddedKafkaKraftBroker));
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return producerProps;
    }

    private Properties getKafkaBrokerProperties()  {

        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap-servers", "localhost:9092");
        kafkaProperties.put("process.roles", "broker,controller");
        kafkaProperties.put("node.id", "1");
        kafkaProperties.put("controller.quorum.voters", "1@localhost:9092");
        kafkaProperties.put("listener.security.protocol.map",
                            "CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT");
        kafkaProperties.put("listeners", "PLAINTEXT://localhost:9092");
        kafkaProperties.put("controller.listener.names", "CONTROLLER");
        kafkaProperties.put("transaction.state.log.replication.factor", "1");
        kafkaProperties.put("transaction.state.log.min.isr", "1");

        return kafkaProperties;
    }
}