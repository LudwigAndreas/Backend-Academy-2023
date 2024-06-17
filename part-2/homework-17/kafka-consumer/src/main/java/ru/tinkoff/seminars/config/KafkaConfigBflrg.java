package ru.tinkoff.seminars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import ru.tinkoff.seminars.model.Person;

@Configuration
@Profile("bflrg")
public class KafkaConfigBflrg {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Person> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setRecordFilterStrategy(record -> record.value().getId() % 15 == 0);
        return factory;
    }

}
