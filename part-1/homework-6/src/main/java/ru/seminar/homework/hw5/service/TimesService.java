package ru.seminar.homework.hw5.service;

import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.model.StatusEnumEntity;

import java.util.Map;
import java.util.UUID;

public interface TimesService {

    Map<StatusEnumEntity, Long> getAverageTime();

    Map<StatusEnumEntity, Long> getTimeByTask(String id) throws NotFoundException;

    Long getTimeByTaskAndStatus(String id, StatusEnumEntity statusEnumEntity) throws NotFoundException;

    Long getAverageTimeByStatus(StatusEnumEntity statusEnumEntity);

}
