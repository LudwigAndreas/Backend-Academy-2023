package ru.tinkoff.seminars.topics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.seminars.topics.dto.TopicDto;
import ru.tinkoff.seminars.topics.model.topic.TopicEntity;

@Mapper
public interface TopicsMapper {

    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastModifiedDate", source = "lastEditDate")
    TopicEntity toModel(TopicDto topicDto);


    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastEditDate", source = "lastModifiedDate")
    TopicDto toDto(TopicEntity topicEntity);

}
