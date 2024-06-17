package ru.tinkoff.seminars.topics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.seminars.topics.config.RedisConfiguration;
import ru.tinkoff.seminars.topics.dto.TopicDto;
import ru.tinkoff.seminars.topics.service.TopicsService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicsController {

    private final Logger logger = LoggerFactory.getLogger(TopicsController.class);

    private final TopicsService topicsService;

    public TopicsController(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TopicDto> getAllTopics(
            @RequestParam(defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromdate,
            @RequestParam(defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate todate,
            @RequestParam(defaultValue = "", required = false) String tagged) {

        logger.trace("getAllTopics: fromdate={}, todate={}, tagged={}", fromdate, todate, tagged);

        return topicsService.getAllTopics(fromdate, todate, Arrays.asList(tagged.split(",")))
                .doOnNext(topic -> logger.trace("Processing element {}", topic))
                .delayElements(Duration.ofSeconds(1)); // simulate slow processing
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TopicDto> createTopic(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody TopicDto topicDto,
            @RequestParam(defaultValue = "false", required = false) boolean preview) {
        logger.trace("createTopic: topicDto={}, preview={}", topicDto, preview);
        logger.info("User {} is creating a new topic", jwt.getClaimAsString("preferred_username"));
        return topicsService.createTopic(topicDto, preview);
    }

    @GetMapping("/{topicId}")
    public Mono<TopicDto> getTopic(@PathVariable Long topicId) {
        return topicsService.getTopic(topicId);
    }

    @PutMapping("/{topicId}")
    public Mono<TopicDto> updateTopic(
            @PathVariable Long topicId,
            @RequestBody TopicDto topicDto,
            @RequestParam(defaultValue = "false", required = false) boolean preview) {
        return topicsService.updateTopic(topicId, topicDto, preview);
    }

    @DeleteMapping("/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTopic(@PathVariable Long topicId) {
        return topicsService.deleteTopic(topicId);
    }

    @PostMapping("/{topicId}/like")
    public Mono<TopicDto> likeTopic(@PathVariable Long topicId,
                                    @RequestParam(defaultValue = "false", required = false) boolean preview) {
        return topicsService.likeTopic(topicId, preview);
    }

    @DeleteMapping("/{topicId}/like")
    public Mono<TopicDto> unlikeTopic(@PathVariable Long topicId,
                                      @RequestParam(defaultValue = "false", required = false) boolean preview) {
        return topicsService.unlikeTopic(topicId, preview);
    }

    @PostMapping("/{topicId}/dislike")
    public Mono<TopicDto> dislikeTopic(@PathVariable Long topicId,
                                       @RequestParam(defaultValue = "false", required = false) boolean preview) {
        return topicsService.dislikeTopic(topicId, preview);
    }

    @DeleteMapping("/{topicId}/dislike")
    public Mono<TopicDto> undislikeTopic(@PathVariable Long topicId,
                                         @RequestParam(defaultValue = "false", required = false) boolean preview) {
        return topicsService.undislikeTopic(topicId, preview);
    }

}
