package ru.tinkoff.seminars.service.kafka;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.tinkoff.seminars.model.Person;

@Service
@Profile("!bfcrg")
public class KafkaConsumerService {

    private static final String topic = "${kafka.topic}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @PostConstruct
    public void init() {
        logger.info("Consumer created for topic: {} with groupId: {}", topic, kafkaConsumerGroupId);
    }

    @KafkaListener(topics = topic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=ru.tinkoff.seminars.model.Person"})
    public Person getPerson(
            @Payload Person person,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        logger.info("Received person: {} from partition: {}", person.getId(), partition);
        return person;
    }


}
