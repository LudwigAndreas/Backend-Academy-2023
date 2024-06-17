package ru.tinkoff.seminars.topics.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.seminars.topics.dto.TopicDto;

import java.time.LocalDate;
import java.util.List;

public interface TopicsService {

    Flux<TopicDto> getAllTopics(LocalDate fromdate,
                                LocalDate todate,
                                List<String> tagged);

//    Mono<Page<TopicDto>> getAllTopicsPageable(Pageable pageable,
//                                              LocalDate fromdate,
//                                              LocalDate todate,
//                                              List<String> tagged);

    Mono<TopicDto> createTopic(TopicDto topicDto, boolean preview);

    Mono<TopicDto> getTopic(Long topicId);

    Mono<TopicDto> updateTopic(Long topicId, TopicDto topicDto, boolean preview);

    Mono<Void> deleteTopic(Long topicId);

    Mono<TopicDto> likeTopic(Long topicId, boolean preview);

    Mono<TopicDto> unlikeTopic(Long topicId, boolean preview);

    Mono<TopicDto> dislikeTopic(Long topicId, boolean preview);

    Mono<TopicDto> undislikeTopic(Long topicId, boolean preview);

}
