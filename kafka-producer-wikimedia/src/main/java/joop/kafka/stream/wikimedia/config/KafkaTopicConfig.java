package joop.kafka.stream.wikimedia.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    @Value("${kafka.topic.wikimedia}")
    private String topic;


    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topic).build();
    }
}
