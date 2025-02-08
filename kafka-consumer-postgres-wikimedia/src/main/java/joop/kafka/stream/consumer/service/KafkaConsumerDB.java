package joop.kafka.stream.consumer.service;

import joop.kafka.stream.consumer.entity.WikimediaData;
import joop.kafka.stream.consumer.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerDB {

    private static final Logger log = LoggerFactory.getLogger(
            KafkaConsumerDB.class);

    private final WikimediaDataRepository wikimediaDataRepository;


    public KafkaConsumerDB(WikimediaDataRepository wikimediaDataRepository) {this.wikimediaDataRepository = wikimediaDataRepository;}

    @KafkaListener(
            topics = "${kafka.topic.wikimedia}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String messageEvent){
        log.info("Message Event received --> {} ", messageEvent);
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setMessage(messageEvent);
        wikimediaDataRepository.save(wikimediaData);
    }
}
