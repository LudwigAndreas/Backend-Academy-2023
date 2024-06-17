package ru.tinkoff.seminars.topics.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.UUID;

@Data
public abstract class BaseEntity {

    @Id
    private Long id;

    @Version
    private Integer version;

    @CreatedBy
    @Field("author_id")
    private UUID authorId;

    @CreatedDate
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Field("created_date")
    private LocalDate createdDate;

    @LastModifiedBy
    @Field("last_modified_by")
    private UUID lastModifiedBy;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Field("last_modified_date")
    private LocalDate lastModifiedDate;

    @Field("deleted")
    private boolean deleted = Boolean.FALSE;
}
