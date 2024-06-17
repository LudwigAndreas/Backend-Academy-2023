package ru.tinkoff.seminars.topics.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;

import java.util.UUID;


public interface TopicsMongoRepository extends ReactiveMongoRepository<TopicEntity, Long> {

    Flux<TopicEntity> findAllBy(Pageable pageable);

    Flux<TopicEntity> findAllByDeletedFalse(Pageable pageable);

    Mono<TopicEntity> findByIdAndDeletedFalse(Long id);

//    @Aggregation("{ $addFields:  {liked:  }}")
//    Flux<TopicEntity> findAllWithLikeStatus(UUID userId, Pageable pageable);

//    Mono<Page<TopicModel>> findByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate , Pageable pageable);
}
