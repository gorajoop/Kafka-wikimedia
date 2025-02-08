package joop.kafka.stream.wikimedia;

import joop.kafka.stream.wikimedia.service.WikimediaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerWikimediaApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerWikimediaApplication.class, args);
    }

    @Autowired
    private WikimediaProducer wikimediaProducer;

    @Autowired
    private AppRunner appRunner;

    @Override
    public void run(String... args)throws Exception{
        //appRunner.run();
        wikimediaProducer.sendMessage();
    }
}
