package ru.seminar.homework.hw5.repository.impl;

import org.springframework.stereotype.Repository;
import ru.seminar.homework.hw5.exception.AlreadyExistsException;
import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.generator.TaskIdGenerator;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;
import ru.seminar.homework.hw5.repository.TaskRepository;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final Map<String, TaskEntity> taskMap;
    private final Queue<TaskEntity> taskQueue;

    public TaskRepositoryImpl() {
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.taskMap = new HashMap<>();
    }

    @Override
    public List<TaskEntity> findAll() {
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.addAll(taskMap.values());
        tasks.addAll(taskQueue);
        return tasks;
    }

    @Override
    public Optional<TaskEntity> findById(String s) {
        if (taskMap.containsKey(s)) {
            return Optional.of(taskMap.get(s));
        } else if (taskQueue.stream().anyMatch(task -> task.getId().equals(s))) {
            return taskQueue.stream().filter(task -> task.getId().equals(s)).findFirst();
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(TaskEntity entity) throws NotFoundException {
        if (taskMap.containsKey(entity.getId())) {
            taskMap.remove(entity.getId());
        } else if (taskQueue.stream().anyMatch(task -> task.getId().equals(entity.getId()))) {
            taskQueue.removeIf(task -> task.getId().equals(entity.getId()));
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteById(String s) throws NotFoundException {
        if (taskMap.containsKey(s)) {
            taskMap.remove(s);
        } else if (taskQueue.stream().anyMatch(task -> task.getId().equals(s))) {
            taskQueue.removeIf(task -> task.getId().equals(s));
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public TaskEntity save(TaskEntity entity) throws NotFoundException, AlreadyExistsException {
        if (taskMap.containsKey(entity.getId()) ||
                taskQueue.stream().anyMatch(task -> task.getId().equals(entity.getId()))) {
            throw new AlreadyExistsException();
        } else {
            entity.setId(TaskIdGenerator.getInstance().generateId());
            if (entity.getStatus().equals(StatusEnumEntity.WAITING)) {
                taskQueue.add(entity);
            } else {
                taskMap.put(entity.getId(), entity);
            }
            return entity;
        }
    }

    @Override
    public List<TaskEntity> saveAll(List<TaskEntity> objects) throws NotFoundException, AlreadyExistsException {
        List<TaskEntity> savedTasks = new ArrayList<>();
        for (TaskEntity task : objects) {
            savedTasks.add(save(task));
        }
        return savedTasks;
    }

    @Override
    public boolean existsById(String s) {
        return taskMap.containsKey(s) || taskQueue.stream().anyMatch(task -> task.getId().equals(s));
    }

    @Override
    public long count() {
        return taskMap.size() + taskQueue.size();
    }

    @Override
    public List<TaskEntity> findAllByStatus(StatusEnumEntity statusEnumEntity) {
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.addAll(taskMap.values());
        tasks.addAll(taskQueue);
        return tasks;
    }

    @Override
    public Optional<TaskEntity> nextWaitingTask() {
        if (taskQueue.isEmpty()) {
            return Optional.empty();
        } else {
            TaskEntity task = taskQueue.poll();
            TaskEntity result = new TaskEntity(task);
            task.setStatus(StatusEnumEntity.PROCESSED);
            taskMap.put(task.getId(), task);
            return Optional.of(result);
        }
    }

    @Override
    public void deleteAll() {
        taskMap.clear();
        taskQueue.clear();
    }
}