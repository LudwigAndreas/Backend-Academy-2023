package ru.seminar.homework.hw5.service.impl;

import org.springframework.stereotype.Service;
import ru.seminar.homework.hw5.exception.NotFoundException;
import ru.seminar.homework.hw5.model.StatusEnumEntity;
import ru.seminar.homework.hw5.model.TaskEntity;
import ru.seminar.homework.hw5.repository.TaskRepository;
import ru.seminar.homework.hw5.service.TimesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TimesServiceImpl implements TimesService {

    private final TaskRepository taskRepository;

    public TimesServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Map<StatusEnumEntity, Long> getAverageTime() {
        List<TaskEntity> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) return new HashMap<>();
        Map<StatusEnumEntity, Long> averageTime = new HashMap<>();
        for (StatusEnumEntity status : StatusEnumEntity.values()) {
            averageTime.put(status, 0L);
        }
        for (TaskEntity task : tasks) {
            averageTime.put(task.getStatus(), averageTime.get(task.getStatus()) + task.getTimes().get(task.getStatus()));
        }
        for (StatusEnumEntity status : StatusEnumEntity.values()) {
            averageTime.put(status, averageTime.get(status) / tasks.size());
        }
        return averageTime;
    }

    @Override
    public Map<StatusEnumEntity, Long> getTimeByTask(String id) throws NotFoundException {
        TaskEntity task = taskRepository.findById(id).orElseThrow(NotFoundException::new);
        return task.getTimes();
    }

    @Override
    public Long getTimeByTaskAndStatus(String id, StatusEnumEntity statusEnumEntity) throws NotFoundException {
        TaskEntity task = taskRepository.findById(id).orElseThrow(NotFoundException::new);
        return task.getTimes().get(statusEnumEntity);
    }

    @Override
    public Long getAverageTimeByStatus(StatusEnumEntity statusEnumEntity) {
        List<TaskEntity> tasks = taskRepository.findAll();
        Long averageTime = 0L;
        for (TaskEntity task : tasks) {
            averageTime += task.getTimes().get(statusEnumEntity);
        }
        return averageTime / tasks.size();
    }
}
