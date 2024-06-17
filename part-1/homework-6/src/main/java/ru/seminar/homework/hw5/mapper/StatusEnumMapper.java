package ru.seminar.homework.hw5.mapper;

import io.reflectoring.model.TaskEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface StatusEnumMapper {

    StatusEnumEntity taskEnumToStatusEnumEntity(TaskEnum taskEnum);

    TaskEnum statusEntityToTaskEnum(StatusEnumEntity statusEnumEntity);
}
