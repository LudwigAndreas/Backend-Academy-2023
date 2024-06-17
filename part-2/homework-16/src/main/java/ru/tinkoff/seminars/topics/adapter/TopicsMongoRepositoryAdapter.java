package ru.tinkoff.seminars.topics.adapter;

import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;
import ru.tinkoff.seminars.topics.repository.TopicsMongoRepository;
import ru.tinkoff.seminars.topics.repository.TopicsRepository;

@Component
public class TopicsMongoRepositoryAdapter implements TopicsRepository {

    private final TopicsMongoRepository topicsMongoRepository;

    private final ReactiveHashOperations<String, Long, TopicEntity> reactiveHashOperations;

    public TopicsMongoRepositoryAdapter(TopicsMongoRepository topicsMongoRepository, ReactiveHashOperations<String, Long, TopicEntity> reactiveHashOperations) {
        this.topicsMongoRepository = topicsMongoRepository;
        this.reactiveHashOperations = reactiveHashOperations;
    }

    private Mono<TopicEntity> updateRedisCache(TopicEntity topicEntity) {
        return reactiveHashOperations.put("topics", topicEntity.getId(), topicEntity)
            .thenReturn(topicEntity);
    }

    private void deleteFromRedisCache(Long topicId) {
        reactiveHashOperations.remove("topics", topicId).subscribe();
    }

    public Mono<TopicEntity> save(TopicEntity topicEntity) {
        return topicsMongoRepository.save(topicEntity)
            .flatMap(this::updateRedisCache);
    }

    public Mono<TopicEntity> findById(Long topicId) {
        return reactiveHashOperations.get("topics", topicId)
            .switchIfEmpty(topicsMongoRepository.findByIdAndDeletedFalse(topicId)
                .flatMap(this::updateRedisCache));
    }

    public Mono<Void> deleteById(Long topicId) {
        return topicsMongoRepository.deleteById(topicId)
            .doOnSuccess(v -> deleteFromRedisCache(topicId));
    }

    @Override
    public Mono<Void> deleteAll() {
        return topicsMongoRepository.deleteAll()
                .doOnSuccess(v -> reactiveHashOperations.delete("topics"));
    }

    public Flux<TopicEntity> findAll() {
        return topicsMongoRepository.findAll();
    }
}
