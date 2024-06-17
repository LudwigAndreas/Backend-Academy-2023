package ru.tinkoff.seminars.topics.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.seminars.topics.dto.TopicDto;
import ru.tinkoff.seminars.topics.mapper.TopicsMapper;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;
import ru.tinkoff.seminars.topics.repository.TopicsRepository;
import ru.tinkoff.seminars.topics.service.TopicsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class TopicsServiceImpl implements TopicsService {

    private final Logger logger = LoggerFactory.getLogger(TopicsServiceImpl.class);

    private final TopicsRepository topicsRepository;

    private final TopicsMapper topicsMapper;

    public TopicsServiceImpl(TopicsRepository topicsRepository, TopicsMapper topicsMapper) {
        this.topicsRepository = topicsRepository;
        this.topicsMapper = topicsMapper;
    }

    @Override
    public Flux<TopicDto> getAllTopics(LocalDate fromdate,
                                       LocalDate todate,
                                       List<String> tagged) {
        return topicsRepository.findAll().map(topicsMapper::toDto);
    }

    @Override
    public Mono<TopicDto> createTopic(TopicDto topicDto, boolean preview) {
        if (Objects.isNull(topicDto)) {
            return Mono.error(new IllegalArgumentException("TopicDto must not be null"));
        } else {
            Mono<TopicEntity> result = topicsRepository.save(topicsMapper.toModel(topicDto));
            logger.trace("createTopic: topicDto={}, preview={}", topicDto, preview);

            return result.flatMap(savedTopic -> {
                if (preview) {
                    return Mono.just(topicsMapper.toDto(savedTopic));
                } else {
                    return Mono.empty();
                }
            });
        }
    }

    @Override
    public Mono<TopicDto> getTopic(Long topicId) {
        return topicsRepository.findById(topicId).map(topicsMapper::toDto);
    }

    @Override
    public Mono<TopicDto> updateTopic(Long topicId, TopicDto topicDto, boolean preview) {
        if (Objects.isNull(topicDto)) {
            return Mono.error(new IllegalArgumentException("TopicDto must not be null"));
        } else {
            Mono<TopicEntity> result = topicsRepository.findById(topicId)
                    .map(topic -> {
                        topic.setTitle(topicDto.getTitle());
                        topic.setBody(topicDto.getBody());
                        topic.setTags(topicDto.getTags());
                        return topic;
                    })
                    .flatMap(topicsRepository::save);

            return result.flatMap(savedTopic -> {
                if (preview) {
                    return Mono.just(topicsMapper.toDto(savedTopic));
                } else {
                    return Mono.empty();
                }
            });
        }
    }

    @Override
    public Mono<Void> deleteTopic(Long topicId) {
        return topicsRepository.findById(topicId)
                .map(topic -> {
                    topic.setDeleted(true);
                    return topic;
                })
                .flatMap(topicsRepository::save)
                .then();
    }

    @Override
    public Mono<TopicDto> likeTopic(Long topicId, boolean preview) {
        return Mono.empty();
    }

    @Override
    public Mono<TopicDto> unlikeTopic(Long topicId, boolean preview) {
        return Mono.empty();
    }

    @Override
    public Mono<TopicDto> dislikeTopic(Long topicId, boolean preview) {
        return Mono.empty();
    }

    @Override
    public Mono<TopicDto> undislikeTopic(Long topicId, boolean preview) {
        return Mono.empty();
    }
}
