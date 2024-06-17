package ru.tinkoff.seminars.topics.model.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "topic_like")
public class TopicLike {

    @Field("user_id")
    private UUID userId;

    @Field("like_date")
    private LocalDate likeDate;

}
