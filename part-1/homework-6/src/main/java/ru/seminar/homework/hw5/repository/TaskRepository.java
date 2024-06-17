package ru.seminar.homework.hw5.repository;

import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends EducationalRepository<TaskEntity, String> {

    List<TaskEntity> findAllByStatus(StatusEnumEntity statusEnumEntity);

     Optional<TaskEntity> nextWaitingTask();

    void deleteAll();
}