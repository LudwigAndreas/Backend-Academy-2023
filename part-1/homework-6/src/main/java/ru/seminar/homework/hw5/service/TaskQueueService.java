package ru.seminar.homework.hw5.service;

import ru.seminar.homework.hw5.model.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskQueueService {

    void addToQueue(TaskEntity taskEntity);

    TaskEntity getFromQueue();

    Optional<TaskEntity> getFromQueue(String id);

    void deleteFromQueue(TaskEntity taskEntity);

    void deleteFromQueue(String id);

    void deleteAllFromQueue();

    List<TaskEntity> getAllFromQueue();
}
