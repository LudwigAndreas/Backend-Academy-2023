package ru.seminar.homework.hw5.service.impl;

import org.springframework.stereotype.Service;
import ru.seminar.homework.hw5.exception.AlreadyExistsException;
import ru.seminar.homework.hw5.exception.InvalidTaskStatusException;
import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;
import ru.seminar.homework.hw5.repository.TaskRepository;
import ru.seminar.homework.hw5.service.TaskQueueService;
import ru.seminar.homework.hw5.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskQueueService taskQueueService;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskQueueService taskQueueService, TaskRepository taskRepository) {
        this.taskQueueService = taskQueueService;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity createTask() {
        TaskEntity taskEntity = new TaskEntity();
        taskQueueService.addToQueue(taskEntity);
        return taskEntity;
    }

    @Override
    public TaskEntity updateTask(TaskEntity taskEntity) {
        if (taskEntity == null) throw new IllegalArgumentException("TaskEntity can't be null");
        else if (taskEntity.getId() == null) {
            throw new IllegalArgumentException("TaskEntity id can't be null");
        }
        taskQueueService.getFromQueue(taskEntity.getId()).ifPresent(taskEntity1 -> {
            taskEntity1.setStatus(taskEntity.getStatus());
        });
        if (taskEntity.getStatus() == StatusEnumEntity.CLOSE || taskEntity.getStatus() == StatusEnumEntity.CANCEL) {
            try {
                taskRepository.save(taskEntity);
            } catch (NotFoundException | AlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        return taskEntity;
    }

    @Override
    public TaskEntity updateTask(String id, StatusEnumEntity status) {
        if (id == null) {
            throw new IllegalArgumentException("TaskEntity id can't be null");
        }
        taskQueueService.getFromQueue(id).ifPresent(taskEntity1 -> {
            taskEntity1.setStatus(status);
        });
        Optional<TaskEntity> taskEntity = taskQueueService.getFromQueue(id);
        if (taskEntity.isEmpty()) {
            throw new NotFoundException("Task not found");
        }
        if (status == StatusEnumEntity.CLOSE || status == StatusEnumEntity.CANCEL) {
            try {
                taskRepository.save(taskEntity.get());
            } catch (NotFoundException | AlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        return taskEntity.get();
    }

    @Override
    public void deleteTaskById(String id) {
        taskQueueService.deleteFromQueue(id);
    }

    @Override
    public TaskEntity getTask(String id) {
        Optional<TaskEntity> task = taskQueueService.getFromQueue(id);
        if (task.isEmpty()) {
            return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        }
        return task.get();
    }

    @Override
    public List<TaskEntity> getTasksByStatus(StatusEnumEntity statusEnumEntity) {
        return taskQueueService.getAllFromQueue().stream().filter(taskEntity -> taskEntity.getStatus() == statusEnumEntity).toList();
    }

    @Override
    public Map<StatusEnumEntity, List<TaskEntity>> getAllIncompleteTasks() {
        Map<StatusEnumEntity, List<TaskEntity>> result = new HashMap<>();
        for (StatusEnumEntity statusEnumEntity : StatusEnumEntity.values()) {
            List<TaskEntity> tasksByStatus = getTasksByStatus(statusEnumEntity);
            if (!tasksByStatus.isEmpty())
                result.put(statusEnumEntity, getTasksByStatus(statusEnumEntity));
        }
        return result;
    }

    @Override
    public TaskEntity getFirstTask() {
        return taskQueueService.getFromQueue();
    }
}
