package ru.tinkoff.seminars.topics.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;

public interface TopicsRepository {

    Mono<TopicEntity> save(TopicEntity topicEntity);

    Mono<TopicEntity> findById(Long topicId);

    Flux<TopicEntity> findAll();

    Mono<Void> deleteById(Long topicId);

    Mono<Void> deleteAll();

}
