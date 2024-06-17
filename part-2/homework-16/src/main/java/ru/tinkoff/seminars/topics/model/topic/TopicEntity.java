package ru.tinkoff.seminars.topics.model.topic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.tinkoff.seminars.topics.model.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "topic")
@EqualsAndHashCode(callSuper = true)
public class TopicEntity extends BaseEntity {

    @Transient
    public static final String SEQUENCE_NAME = "topics_sequence";

    @TextIndexed
    @Field("title")
    private String title;

    @Field("body")
    private String body;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Field("closed_at")
    private LocalDate closedAt;

    @Field("closed_reason")
    private String closedReason;

    @DocumentReference
    private List<TopicLike> likes;

    @DocumentReference
    private List<TopicDislike> dislikes;

    private List<String> tags;

    private boolean liked;

    private boolean disliked;

}
