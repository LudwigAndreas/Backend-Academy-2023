package ru.tinkoff.seminars.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.seminars.model.Person;
import ru.tinkoff.seminars.repository.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaMessagingService {

    private final Logger logger = LoggerFactory.getLogger(KafkaMessagingService.class);
    private final KafkaTemplate<String, Person> kafkaTemplate;
    private final PersonRepository personRepository;
    private final Environment env;
    @Value("${kafka.topic}")
    private String sendClientTopic;

    @PostConstruct
    public void init() {

        logger.info("producer created with {} active profiles", env.getActiveProfiles());

        sendPersons();
    }

    public void sendPersons() {
        long maxId = personRepository.findLargestId();
        logger.info("Sending data to kafka topic: {}", sendClientTopic);

        for (long i = 1; i < maxId; i += 1000) {
            List<Person> persons = personRepository.findAll(PageRequest.of(0, 1000)).getContent();

            persons.stream()
                    .map(person -> new ProducerRecord<>(sendClientTopic, person.getId().toString(), person))
                    .forEach(kafkaTemplate::send);
        }
    }

//    public void sendCallback(SendResult<String, Person> result, Throwable e) {
//        if (e != null) {
//            logger.error("Error sending record: {}", result.getProducerRecord().key(), e);
//        } else {
//            logger.debug("Record sent to topic: {}, partition: {}, offset: {}",
//                    result.getRecordMetadata().topic(),
//                    result.getRecordMetadata().partition(),
//                    result.getRecordMetadata().offset());
//        }
//    }


}
