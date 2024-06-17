package ru.seminar.homework.hw5.service;

import ru.seminar.homework.hw5.exception.AlreadyExistsException;
import ru.seminar.homework.hw5.exception.InvalidTaskStatusException;
import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskService {
    TaskEntity createTask();

    TaskEntity updateTask(TaskEntity taskEntity);

    TaskEntity updateTask(String id, StatusEnumEntity status);

    void deleteTaskById(String id) throws NotFoundException;

    TaskEntity getTask(String id) throws NotFoundException;

    List<TaskEntity> getTasksByStatus(StatusEnumEntity statusEnumEntity);

    Map<StatusEnumEntity, List<TaskEntity>> getAllIncompleteTasks();

    TaskEntity getFirstTask();

}
