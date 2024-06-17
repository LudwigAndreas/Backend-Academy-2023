package ru.tinkoff.seminars.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.tinkoff.seminars.service.KafkaMessagingService;

@Component
public class KafkaProducerLineRunner implements CommandLineRunner {

    private final KafkaMessagingService kafkaMessagingService;

    public KafkaProducerLineRunner(KafkaMessagingService kafkaMessagingService) {
        this.kafkaMessagingService = kafkaMessagingService;
    }

    @Override
    public void run(String... args) throws Exception {

        kafkaMessagingService.sendPersons();

    }
}
