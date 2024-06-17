package ru.tinkoff.seminars.topics.config;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;

@Configuration
public class RedisConfiguration {

    private final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    public static final String TOPICS_KEY = "topics";
    @Bean
    public ReactiveHashOperations<String, Long, TopicEntity> hashOperations(ReactiveRedisConnectionFactory factory) {
        logger.trace("Configured Redis hash operations for topics");
        var template = new ReactiveRedisTemplate<>(
                factory,
                RedisSerializationContext.<String, TopicEntity>newSerializationContext(new GenericJackson2JsonRedisSerializer())
                        .hashKey(new GenericToStringSerializer<>(Long.class))
                        .hashValue(new Jackson2JsonRedisSerializer<>(TopicEntity.class))
                        .build());
        return template.opsForHash();
    }


    @PreDestroy
    public void close() {
        logger.debug("Closing Redis connection");
    }
}
