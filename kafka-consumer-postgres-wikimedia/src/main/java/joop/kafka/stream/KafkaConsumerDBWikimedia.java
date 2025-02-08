package joop.kafka.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaConsumerDBWikimedia {
    private static final Logger log = LoggerFactory.getLogger(
            KafkaConsumerDBWikimedia.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerDBWikimedia.class, args);
    }

    /*@Autowired
    DatabasePingService databasePingService;
    @Override
    public void run(String... args) throws Exception {

        log.info(" Database result : {}",databasePingService.pingDatabase());
    }*/
}