package ru.seminar.homework.hw5.service.impl;

import org.springframework.stereotype.Service;
import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.generator.TaskIdGenerator;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;
import ru.seminar.homework.hw5.repository.TaskRepository;
import ru.seminar.homework.hw5.service.TaskQueueService;

import java.util.List;
import java.util.Optional;

@Service
public class TaskQueueServiceImpl implements TaskQueueService {

    private final TaskRepository taskRepository;

    public TaskQueueServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addToQueue(TaskEntity taskEntity) {
        if (taskEntity == null) throw new IllegalArgumentException("TaskEntity can't be null");
        else if (taskEntity.getId() == null) {
            TaskIdGenerator.getInstance().generateId();
        }
        if (taskEntity.getStatus() == null) {
            taskEntity.setStatus(StatusEnumEntity.WAITING);
        }
        taskRepository.save(taskEntity);
    }

    @Override
    public TaskEntity getFromQueue() {
        return taskRepository.nextWaitingTask().orElseThrow(NotFoundException::new);
    }

    @Override
    public Optional<TaskEntity> getFromQueue(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public void deleteFromQueue(TaskEntity taskEntity) {
        taskRepository.delete(taskEntity);
    }

    @Override
    public void deleteFromQueue(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAllFromQueue() {
        taskRepository.deleteAll();
    }

    @Override
    public List<TaskEntity> getAllFromQueue() {
        return taskRepository.findAll();
    }
}
