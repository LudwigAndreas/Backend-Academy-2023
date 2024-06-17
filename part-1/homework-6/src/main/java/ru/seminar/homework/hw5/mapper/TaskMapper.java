package ru.seminar.homework.hw5.mapper;

import io.reflectoring.model.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mappings({
            @Mapping(target = "times", source = "times", qualifiedByName = "offsetDateTimeToStringMap"),
    })
    Task map(TaskEntity taskEntity);

    @Mappings({
            @Mapping(target = "times", source = "times", qualifiedByName = "stringToOffsetDateTimeMap"),
    })
    TaskEntity map(Task task);

    @Named("offsetDateTimeToStringMap")
    default Map<String, OffsetDateTime> offsetDateTimeToStringMap(Map<StatusEnumEntity, Long> times) {
        Map<String, OffsetDateTime> result = new HashMap<>();
        times.forEach((key, value) -> result.put(key.name(), OffsetDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault())));
        return result;
    }

    @Named("stringToOffsetDateTimeMap")
    default Map<StatusEnumEntity, Long> stringToOffsetDateTimeMap(Map<String, OffsetDateTime> times) {
        Map<StatusEnumEntity, Long> result = new HashMap<>();
        times.forEach((key, value) -> result.put(StatusEnumEntity.valueOf(key), value.toInstant().toEpochMilli()));
        return result;
    }

}
