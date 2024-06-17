package ru.tinkoff.seminars.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.partitions}")
    private int partitions;

    @Value("${kafka.replication-factor}")
    private short replicationFactor;


    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicationFactor)
                .build();
    }
}
