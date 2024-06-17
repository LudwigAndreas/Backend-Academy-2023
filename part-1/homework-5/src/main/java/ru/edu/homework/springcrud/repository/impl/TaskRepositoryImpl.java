package ru.edu.homework.springcrud.repository.impl;

import org.springframework.stereotype.Repository;
import ru.edu.homework.springcrud.db.TaskIdGenerator;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.exception.task.NotCompletedException;
import ru.edu.homework.springcrud.model.TaskStatus;
import ru.edu.homework.springcrud.model.TriggeredTask;
import ru.edu.homework.springcrud.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    List<TriggeredTask> tasks;


    public TaskRepositoryImpl() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public List<TriggeredTask> findAll() {
        return tasks;
    }

    @Override
    public Optional<TriggeredTask> findById(Long id) {
        for (TriggeredTask task : tasks) {
            if (task.getId().equals(id)) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(TriggeredTask entity) {
        for (TriggeredTask task : tasks) {
            if (task.getId().equals(entity.getId())) {
                if (task.getStatus() != TaskStatus.DONE) {
                    throw new NotCompletedException("Task is not completed yet");
                }
                tasks.remove(task);
                return;
            }
        }
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        for (TriggeredTask task : tasks) {
            if (task.getId().equals(id)) {
                if (task.getStatus() != TaskStatus.DONE) {
                    throw new NotCompletedException("Task is not completed yet");
                }
                tasks.remove(task);
                return;
            }
        }
    }

    @Override
    public TriggeredTask save(TriggeredTask entity) throws NotFoundException, AlreadyExistsException {
        if (entity.getId() == null) {
            entity.setId(TaskIdGenerator.getInstance().generateId());
        }
        for (TriggeredTask task : tasks) {
            if (task.getId().equals(entity.getId()) && !Objects.equals(task.getSourceCategoryId(), entity.getSourceCategoryId()) && !Objects.equals(task.getTargetCategoryId(), entity.getTargetCategoryId())) {
                throw new AlreadyExistsException("Task with id " + entity.getId() + " already exists");
            }
        }
        tasks.add(entity);
        return entity;
    }

    @Override
    public List<TriggeredTask> saveAll(List<TriggeredTask> entities) throws NotFoundException, AlreadyExistsException {
        for (int i = 0; i < entities.size() - 1; i++) {
            if (entities.get(i).getId().equals(entities.get(i + 1).getId())) {
                throw new AlreadyExistsException("Task with id " + entities.get(i).getId() + " already exists");
            }
        }
        for (TriggeredTask task : tasks) {
            for (TriggeredTask product : entities) {
                if (task.getId().equals(product.getId())) {
                    throw new AlreadyExistsException("Task with id " + product.getId() + " already exists");
                }
            }
        }
        tasks.addAll(entities);
        return entities;
    }

    @Override
    public boolean existsById(Long id) {
        return tasks.stream().anyMatch(task -> task.getId().equals(id));
    }

    @Override
    public long count() {
        return tasks.size();
    }
}
